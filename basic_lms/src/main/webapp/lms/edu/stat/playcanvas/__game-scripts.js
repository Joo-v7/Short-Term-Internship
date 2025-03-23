// stencil-mask.js
var StencilMask = pc.createScript('stencilMask');

StencilMask.attributes.add('maskID', {
    title: 'Mask ID',
    description: 'This mask id should be an integer between 0 and 7, as it relates as the power of 2 to the actual mask id.',
    type: 'number',
    default: 0,
    min: 0,
    max: 7,
    precision: 0,
});


StencilMask.prototype.initialize = function () {    
    this._setStencil();
    this.on('attr', this._attributeReloading);
};


StencilMask.prototype._setStencil = function () {
    var mask = Math.pow(2, this.maskID);

    var stencil = new pc.StencilParameters({
        readMask: mask,
        writeMask: mask,
        ref: mask,
        zpass: pc.STENCILOP_REPLACE
    });

    this._setModelAsStencil(stencil);
};


StencilMask.prototype._setModelAsStencil = function (stencilParameter) {
    var meshInstances = null;

    if (this.entity.render) {
        meshInstances = this.entity.render.meshInstances;
    }

    if (this.entity.model) {
        meshInstances = this.entity.model.meshInstances;
    }

    if (meshInstances && meshInstances.length > 0) {
        var mat = meshInstances[0].material.clone();
        mat.stencilBack = mat.stencilFront = stencilParameter;

        // Don't write to color, only to stencil
        mat.redWrite = mat.greenWrite = mat.blueWrite = mat.alphaWrite = false;
        meshInstances[0].material = mat;
        mat.update();
    }
};


StencilMask.prototype.swap = function (old) {
    this.on('attr', this._attributeReloading);
};


StencilMask.prototype._attributeReloading = function (name, value, prev) {
    this._setStencil();
};

// stencil-subject.js
var StencilSubject = pc.createScript('stencilSubject');

StencilSubject.attributes.add('showInside', {
    title: 'Show',
    description: 'Determines whether this entity will be shown inside or outside of a mask with the given ID.\n' +
        'If multiple ID\'s are passed: \n' +
        '- If \'Inside\' is chosen, only the parts which are inside all of the given IDs will be shown.\n' +
        '- If \'Outside\' is chosen, the parts which are outside any of the given IDs will be shown.\n',
    type: 'boolean',
    default: true,
    enum: [
        { 'Inside': true },
        { 'Outside': false },
    ],
});

StencilSubject.attributes.add('maskIDs', {
    title: 'Mask IDs',
    description: 'These ID\'s have to be between 0 and 7.',
    type: 'number',
    array: true,
    default: [0],
});


StencilSubject.prototype.postInitialize = function () {
    var i = 0;
    var j = 0;
    this._meshInstances = [];
    
    var renders = this.entity.findComponents('render');

    for (i = 0; i < renders.length; ++i) {
        meshInstances = renders[i].meshInstances;
        for (j = 0; j < meshInstances.length; j++) {
            this._meshInstances.push(meshInstances[j]);
        }
    }

    var models = this.entity.findComponents('model');
    for (i = 0; i < models.length; ++i) {
        meshInstances = models[i].meshInstances;
        for (j = 0; j < meshInstances.length; j++) {
            this._meshInstances.push(meshInstances[j]);
        }
    }

    this._setStencil();

    this.on('attr', this._attributeReloading);
};


StencilSubject.prototype._setStencil = function () {
    if (!this._addMaskIDs()) {
        return;
    }

    var stencil = new pc.StencilParameters({
        readMask: this.maskID,
        writeMask: this.maskID,
        ref: this.maskID,
        func: this.showInside ? pc.FUNC_EQUAL : pc.FUNC_NOTEQUAL,
    });

    this._setStencilForModel(stencil);
    this._setStencilForParticle(stencil);
    this._setStencilForSpine(stencil);
    this._setStencilForSprite(stencil);
};


StencilSubject.prototype._addMaskIDs = function () {
    this.maskID = 0;

    // Check if the mask has the right length
    if (this.maskIDs.length > 8) {
        return false;
    }

    for (var i = 0; i < this.maskIDs.length; ++i) {
        var id = Math.floor(this.maskIDs[i]);

        if (id >= 0 && id < 8) {
            this.maskID += Math.pow(2, id);
        }
        else {
            // Check if the mask has the right length
            return false;
        }
    }

    return true;
};


StencilSubject.prototype._setStencilForModel = function (stencil) {
    for (var i = 0; i < this._meshInstances.length; i++) {
        this._meshInstances[i].layer = this.app.scene.layers.getLayerByName('Before World');
        var mat = this._meshInstances[i].material.clone();
        mat.stencilBack = mat.stencilFront = stencil;
        this._meshInstances[i].material = mat;
    }
};


StencilSubject.prototype._setStencilForParticle = function (stencil) {
    if (this.entity.particlesystem) {
        this.entity.particlesystem.emitter.meshInstance.layer = this.app.scene.layers.getLayerByName('Before World');
        var mat = this.entity.particlesystem.emitter.material;
        mat.stencilBack = mat.stencilFront = stencil;
    }
};


StencilSubject.prototype._setStencilForSpine = function (stencil) {
    if (this.entity.spine) {
        var model = this.entity.spine.spine._model;

    for (var i = 0; i < this._meshInstances.length; i++) {
            this._meshInstances[i].layer = this.app.scene.layers.getLayerByName('Before World');
            var mat = this._meshInstances[i].material;
            mat.stencilBack = mat.stencilFront = stencil;
        }
    }
};


StencilSubject.prototype._setStencilForSprite = function (stencil) {
    if (this.entity.sprite) {
        // Waring: Private API
        var model = this.entity.sprite._model;

        model.meshInstances[0].layer = this.app.scene.layers.getLayerByName('Before World');
        var mat = model.meshInstances[0].material.clone();
        mat.stencilBack = mat.stencilFront = stencil;
        model.meshInstances[0].material = mat;
    }
};


StencilSubject.prototype.swap = function (stencil) {
    this.on('attr', this._attributeReloading);
};


StencilSubject.prototype._attributeReloading = function (name, value, prev) {
    this._setStencil();
};

// orbit-camera.js
var OrbitCamera = pc.createScript('orbitCamera');

OrbitCamera.attributes.add('distanceMax', { type: 'number', default: 0, title: 'Distance Max', description: 'Setting this at 0 will give an infinite distance limit' });
OrbitCamera.attributes.add('distanceMin', { type: 'number', default: 0, title: 'Distance Min' });

OrbitCamera.attributes.add('pitchClamp', { type: "boolean", default: true, title: 'Pitch Angle Clamp Check' });
OrbitCamera.attributes.add('pitchAngleMax', { type: 'number', default: 90, title: 'Pitch Angle Max (degrees)' });
OrbitCamera.attributes.add('pitchAngleMin', { type: 'number', default: -90, title: 'Pitch Angle Min (degrees)' });

OrbitCamera.attributes.add('yawClamp', { type: "boolean", default: true, title: 'yaw Angle Clamp Check' });
OrbitCamera.attributes.add('yawAngleMax', { type: 'number', default: 90, title: 'yaw Angle Max (degrees)' });
OrbitCamera.attributes.add('yawAngleMin', { type: 'number', default: -90, title: 'yaw Angle Min (degrees)' });

OrbitCamera.attributes.add('startPosition', { type: 'entity' });

OrbitCamera.attributes.add('inertiaFactor', {
    type: 'number',
    default: 0,
    title: 'Inertia Factor',
    description: 'Higher value means that the camera will continue moving after the user has stopped dragging. 0 is fully responsive.'
});

OrbitCamera.attributes.add('focusEntity', {
    type: 'entity',
    title: 'Focus Entity',
    description: 'Entity for the camera to focus on. If blank, then the camera will use the whole scene'
});

OrbitCamera.attributes.add('frameOnStart', {
    type: 'boolean',
    default: true,
    title: 'Frame on Start',
    description: 'Frames the entity or scene at the start of the application."'
});


// Property to get and set the distance between the pivot point and camera
// Clamped between this.distanceMin and this.distanceMax
Object.defineProperty(OrbitCamera.prototype, "distance", {
    get: function () {
        return this._targetDistance;
    },

    set: function (value) {
        this._targetDistance = this._clampDistance(value);
    }
});


// Property to get and set the pitch of the camera around the pivot point (degrees)
// Clamped between this.pitchAngleMin and this.pitchAngleMax
// When set at 0, the camera angle is flat, looking along the horizon
Object.defineProperty(OrbitCamera.prototype, "pitch", {
    get: function () {
        return this._targetPitch;
    },

    set: function (value) {
        this._targetPitch = this._clampPitchAngle(value);
    }
});


// Property to get and set the yaw of the camera around the pivot point (degrees)
Object.defineProperty(OrbitCamera.prototype, "yaw", {
    get: function () {
        return this._targetYaw;
    },

    set: function (value) {
        this._targetYaw = this._clampPitchYaw(value);

        // Ensure that the yaw takes the shortest route by making sure that 
        // the difference between the targetYaw and the actual is 180 degrees
        // in either direction
        var diff = this._targetYaw - this._yaw;
        var reminder = diff % 360;
        if (reminder > 180) {
            this._targetYaw = this._yaw - (360 - reminder);
        } else if (reminder < -180) {
            this._targetYaw = this._yaw + (360 + reminder);
        } else {
            this._targetYaw = this._yaw + reminder;
        }
    }
});


// Property to get and set the world position of the pivot point that the camera orbits around
Object.defineProperty(OrbitCamera.prototype, "pivotPoint", {
    get: function () {
        return this._pivotPoint;
    },

    set: function (value) {
        this._pivotPoint.copy(value);
    }
});


// Moves the camera to look at an entity and all its children so they are all in the view
OrbitCamera.prototype.focus = function (focusEntity) {
    // Calculate an bounding box that encompasses all the models to frame in the camera view
    this._buildAabb(focusEntity, 0);

    var halfExtents = this._modelsAabb.halfExtents;

    var distance = Math.max(halfExtents.x, Math.max(halfExtents.y, halfExtents.z));
    distance = (distance / Math.tan(0.5 * this.entity.camera.fov * pc.math.DEG_TO_RAD));
    distance = (distance * 2);

    this.distance = distance;

    this._removeInertia();

    this._pivotPoint.copy(this._modelsAabb.center);
};


OrbitCamera.distanceBetween = new pc.Vec3();

// Set the camera position to a world position and look at a world position
// Useful if you have multiple viewing angles to swap between in a scene
OrbitCamera.prototype.resetAndLookAtPoint = function (resetPoint, lookAtPoint) {
    this.pivotPoint.copy(lookAtPoint);
    this.entity.setPosition(resetPoint);

    this.entity.lookAt(lookAtPoint);

    var distance = OrbitCamera.distanceBetween;
    distance.sub2(lookAtPoint, resetPoint);
    this.distance = distance.length();

    this.pivotPoint.copy(lookAtPoint);

    var cameraQuat = this.entity.getRotation();
    this.yaw = this._calcYaw(cameraQuat);
    this.pitch = this._calcPitch(cameraQuat, this.yaw);

    this._removeInertia();
    this._updatePosition();
};


// Set camera position to a world position and look at an entity in the scene
// Useful if you have multiple models to swap between in a scene
OrbitCamera.prototype.resetAndLookAtEntity = function (resetPoint, entity) {
    this._buildAabb(entity, 0);
    this.resetAndLookAtPoint(resetPoint, this._modelsAabb.center);
};


// Set the camera at a specific, yaw, pitch and distance without inertia (instant cut)
OrbitCamera.prototype.reset = function (yaw, pitch, distance) {
    this.pitch = pitch;
    this.yaw = yaw;
    this.distance = distance;

    this._removeInertia();
};

/////////////////////////////////////////////////////////////////////////////////////////////
// Private methods

OrbitCamera.prototype.initialize = function () {
    var self = this;
    var onWindowResize = function () {
        self._checkAspectRatio();
    };

    window.addEventListener('resize', onWindowResize, false);

    this._checkAspectRatio();

    // Find all the models in the scene that are under the focused entity
    this._modelsAabb = new pc.BoundingBox();
    this._buildAabb(this.focusEntity || this.app.root, 0);

    this.entity.lookAt(this._modelsAabb.center);

    this._pivotPoint = new pc.Vec3();
    this._pivotPoint.copy(this._modelsAabb.center);

    // Calculate the camera euler angle rotation around x and y axes
    // This allows us to place the camera at a particular rotation to begin with in the scene
    var cameraQuat = this.entity.getRotation();

    // Preset the camera
    this._yaw = this._calcYaw(cameraQuat);
    this._pitch = this._clampPitchAngle(this._calcPitch(cameraQuat, this._yaw));
    this.entity.setLocalEulerAngles(this._pitch, this._yaw, 0);

    this._distance = 0;

    this._targetYaw = this._yaw;
    this._targetPitch = this._pitch;

    // If we have ticked focus on start, then attempt to position the camera where it frames
    // the focused entity and move the pivot point to entity's position otherwise, set the distance
    // to be between the camera position in the scene and the pivot point
    if (this.frameOnStart) {
        this.focus(this.focusEntity || this.app.root);
    } else {
        var distanceBetween = new pc.Vec3();
        distanceBetween.sub2(this.entity.getPosition(), this._pivotPoint);
        this._distance = this._clampDistance(distanceBetween.length());
    }

    this._targetDistance = this._distance;

    // Reapply the clamps if they are changed in the editor
    this.on('attr:distanceMin', function (value, prev) {
        this._targetDistance = this._clampDistance(this._distance);
    });

    this.on('attr:distanceMax', function (value, prev) {
        this._targetDistance = this._clampDistance(this._distance);
    });

    this.on('attr:pitchAngleMin', function (value, prev) {
        this._targetPitch = this._clampPitchAngle(this._pitch);
    });

    this.on('attr:pitchAngleMax', function (value, prev) {
        this._targetPitch = this._clampPitchAngle(this._pitch);
    });

    // Focus on the entity if we change the focus entity
    this.on('attr:focusEntity', function (value, prev) {
        if (this.frameOnStart) {
            this.focus(value || this.app.root);
        } else {
            this.resetAndLookAtEntity(this.entity.getPosition(), value || this.app.root);
        }
    });

    this.on('attr:frameOnStart', function (value, prev) {
        if (value) {
            this.focus(this.focusEntity || this.app.root);
        }
    });

    this.on('destroy', function () {
        window.removeEventListener('resize', onWindowResize, false);
    });

    this.pivotPoint.add(this.startPosition.getPosition());
    // this._pitch = 45;
    // this._targetPitch = this._clampPitchAngle(this._pitch);
};

OrbitCamera.prototype.setAngle = function (pos) {
    this._pitch = pos.x;
    this._yaw = pos.y;
};


OrbitCamera.prototype.update = function (dt) {
    // Add inertia, if any
    var t = this.inertiaFactor === 0 ? 1 : Math.min(dt / this.inertiaFactor, 1);
    this._distance = pc.math.lerp(this._distance, this._targetDistance, t);
    this._yaw = pc.math.lerp(this._yaw, this._targetYaw, t);
    this._pitch = pc.math.lerp(this._pitch, this._targetPitch, t);
    // console.log(this.entity.getEulerAngles());
    this._updatePosition();
};


OrbitCamera.prototype._updatePosition = function () {
    // Work out the camera position based on the pivot point, pitch, yaw and distance
    this.entity.setLocalPosition(0, 0, 0);
    this.entity.setLocalEulerAngles(this._pitch, this._yaw, 0);

    var position = this.entity.getPosition();
    position.copy(this.entity.forward);
    position.scale(-this._distance);
    position.add(this.pivotPoint);
    this.entity.setPosition(position);
};


OrbitCamera.prototype._removeInertia = function () {
    this._yaw = this._targetYaw;
    this._pitch = this._targetPitch;
    this._distance = this._targetDistance;
};


OrbitCamera.prototype._checkAspectRatio = function () {
    var height = this.app.graphicsDevice.height;
    var width = this.app.graphicsDevice.width;

    // Match the axis of FOV to match the aspect ratio of the canvas so
    // the focused entities is always in frame
    this.entity.camera.horizontalFov = height > width;
};


OrbitCamera.prototype._buildAabb = function (entity, modelsAdded) {
    var i = 0, j = 0, meshInstances;

    if (entity instanceof pc.Entity) {
        var allMeshInstances = [];
        var renders = entity.findComponents('render');

        for (i = 0; i < renders.length; ++i) {
            meshInstances = renders[i].meshInstances;
            for (j = 0; j < meshInstances.length; j++) {
                allMeshInstances.push(meshInstances[j]);
            }
        }

        var models = entity.findComponents('model');
        for (i = 0; i < models.length; ++i) {
            meshInstances = models[i].meshInstances;
            for (j = 0; j < meshInstances.length; j++) {
                allMeshInstances.push(meshInstances[j]);
            }
        }

        for (i = 0; i < allMeshInstances.length; i++) {
            if (modelsAdded === 0) {
                this._modelsAabb.copy(allMeshInstances[i].aabb);
            } else {
                this._modelsAabb.add(allMeshInstances[i].aabb);
            }

            modelsAdded += 1;
        }
    }

    for (i = 0; i < entity.children.length; ++i) {
        modelsAdded += this._buildAabb(entity.children[i], modelsAdded);
    }

    return modelsAdded;
};


OrbitCamera.prototype._calcYaw = function (quat) {
    var transformedForward = new pc.Vec3();
    quat.transformVector(pc.Vec3.FORWARD, transformedForward);

    return Math.atan2(-transformedForward.x, -transformedForward.z) * pc.math.RAD_TO_DEG;
};


OrbitCamera.prototype._clampDistance = function (distance) {
    if (this.distanceMax > 0) {
        return pc.math.clamp(distance, this.distanceMin, this.distanceMax);
    } else {
        return Math.max(distance, this.distanceMin);
    }
};


OrbitCamera.prototype._clampPitchAngle = function (pitch) {
    // Negative due as the pitch is inversed since the camera is orbiting the entity
    if (this.pitchClamp) return pc.math.clamp(pitch, -this.pitchAngleMax, -this.pitchAngleMin);
    else return pitch;
};

OrbitCamera.prototype._clampPitchYaw = function (yaw) {
    // Negative due as the pitch is inversed since the camera is orbiting the entity
    if (this.yawClamp) return pc.math.clamp(yaw, -this.yawAngleMax, -this.yawAngleMin);
    else return yaw;
};


OrbitCamera.quatWithoutYaw = new pc.Quat();
OrbitCamera.yawOffset = new pc.Quat();

OrbitCamera.prototype._calcPitch = function (quat, yaw) {
    var quatWithoutYaw = OrbitCamera.quatWithoutYaw;
    var yawOffset = OrbitCamera.yawOffset;

    yawOffset.setFromEulerAngles(0, -yaw, 0);
    quatWithoutYaw.mul2(yawOffset, quat);

    var transformedForward = new pc.Vec3();

    quatWithoutYaw.transformVector(pc.Vec3.FORWARD, transformedForward);

    return Math.atan2(transformedForward.y, -transformedForward.z) * pc.math.RAD_TO_DEG;
};

// keyboard-input.js
var KeyboardInput = pc.createScript('keyboardInput');

// initialize code called once per entity
KeyboardInput.prototype.initialize = function() {
    this.orbitCamera = this.entity.script.orbitCamera;
};


KeyboardInput.prototype.postInitialize = function() {
    if (this.orbitCamera) {
        this.startDistance = this.orbitCamera.distance;
        this.startYaw = this.orbitCamera.yaw;
        this.startPitch = this.orbitCamera.pitch;
        this.startPivotPosition = this.orbitCamera.pivotPoint.clone();
    }
};

// update code called every frame
KeyboardInput.prototype.update = function(dt) {
    if (this.orbitCamera) {
        if (this.app.keyboard.wasPressed(pc.KEY_SPACE)) {
            this.orbitCamera.reset(this.startYaw, this.startPitch, this.startDistance);
            this.orbitCamera.pivotPoint = this.startPivotPosition;
        }
    }
};


// touch-input.js
var TouchInput = pc.createScript('touchInput');

TouchInput.attributes.add('orbitSensitivity', {
    type: 'number', 
    default: 0.4, 
    title: 'Orbit Sensitivity', 
    description: 'How fast the camera moves around the orbit. Higher is faster'
});

TouchInput.attributes.add('distanceSensitivity', {
    type: 'number', 
    default: 0.2, 
    title: 'Distance Sensitivity', 
    description: 'How fast the camera moves in and out. Higher is faster'
});

// initialize code called once per entity
TouchInput.prototype.initialize = function() {
    this.orbitCamera = this.entity.script.orbitCamera;
    
    // Store the position of the touch so we can calculate the distance moved
    this.lastTouchPoint = new pc.Vec2();
    this.lastPinchMidPoint = new pc.Vec2();
    this.lastPinchDistance = 0;
    
    if (this.orbitCamera && this.app.touch) {
        // Use the same callback for the touchStart, touchEnd and touchCancel events as they 
        // all do the same thing which is to deal the possible multiple touches to the screen
        this.app.touch.on(pc.EVENT_TOUCHSTART, this.onTouchStartEndCancel, this);
        this.app.touch.on(pc.EVENT_TOUCHEND, this.onTouchStartEndCancel, this);
        this.app.touch.on(pc.EVENT_TOUCHCANCEL, this.onTouchStartEndCancel, this);
        
        this.app.touch.on(pc.EVENT_TOUCHMOVE, this.onTouchMove, this);
        
        this.on('destroy', function() {
            this.app.touch.off(pc.EVENT_TOUCHSTART, this.onTouchStartEndCancel, this);
            this.app.touch.off(pc.EVENT_TOUCHEND, this.onTouchStartEndCancel, this);
            this.app.touch.off(pc.EVENT_TOUCHCANCEL, this.onTouchStartEndCancel, this);

            this.app.touch.off(pc.EVENT_TOUCHMOVE, this.onTouchMove, this);
        });
    }
};


TouchInput.prototype.getPinchDistance = function (pointA, pointB) {
    // Return the distance between the two points
    var dx = pointA.x - pointB.x;
    var dy = pointA.y - pointB.y;    
    
    return Math.sqrt((dx * dx) + (dy * dy));
};


TouchInput.prototype.calcMidPoint = function (pointA, pointB, result) {
    result.set(pointB.x - pointA.x, pointB.y - pointA.y);
    result.scale(0.5);
    result.x += pointA.x;
    result.y += pointA.y;
};


TouchInput.prototype.onTouchStartEndCancel = function(event) {
    // We only care about the first touch for camera rotation. As the user touches the screen, 
    // we stored the current touch position
    var touches = event.touches;
    if (touches.length == 1) {
        this.lastTouchPoint.set(touches[0].x, touches[0].y);
    
    } else if (touches.length == 2) {
        // If there are 2 touches on the screen, then set the pinch distance
        this.lastPinchDistance = this.getPinchDistance(touches[0], touches[1]);
        this.calcMidPoint(touches[0], touches[1], this.lastPinchMidPoint);
    }
};


TouchInput.fromWorldPoint = new pc.Vec3();
TouchInput.toWorldPoint = new pc.Vec3();
TouchInput.worldDiff = new pc.Vec3();


TouchInput.prototype.pan = function(midPoint) {
    var fromWorldPoint = TouchInput.fromWorldPoint;
    var toWorldPoint = TouchInput.toWorldPoint;
    var worldDiff = TouchInput.worldDiff;
    
    // For panning to work at any zoom level, we use screen point to world projection
    // to work out how far we need to pan the pivotEntity in world space 
    var camera = this.entity.camera;
    var distance = this.orbitCamera.distance;
    
    camera.screenToWorld(midPoint.x, midPoint.y, distance, fromWorldPoint);
    camera.screenToWorld(this.lastPinchMidPoint.x, this.lastPinchMidPoint.y, distance, toWorldPoint);
    
    worldDiff.sub2(toWorldPoint, fromWorldPoint);
     
    this.orbitCamera.pivotPoint.add(worldDiff);    
};


TouchInput.pinchMidPoint = new pc.Vec2();

TouchInput.prototype.onTouchMove = function(event) {
    var pinchMidPoint = TouchInput.pinchMidPoint;
    
    // We only care about the first touch for camera rotation. Work out the difference moved since the last event
    // and use that to update the camera target position 
    var touches = event.touches;
    if (touches.length == 1) {
        var touch = touches[0];
        
        this.orbitCamera.pitch -= (touch.y - this.lastTouchPoint.y) * this.orbitSensitivity;
        this.orbitCamera.yaw -= (touch.x - this.lastTouchPoint.x) * this.orbitSensitivity;
        
        this.lastTouchPoint.set(touch.x, touch.y);
    
    } else if (touches.length == 2) {
        // Calculate the difference in pinch distance since the last event
        var currentPinchDistance = this.getPinchDistance(touches[0], touches[1]);
        var diffInPinchDistance = currentPinchDistance - this.lastPinchDistance;
        this.lastPinchDistance = currentPinchDistance;
                
        this.orbitCamera.distance -= (diffInPinchDistance * this.distanceSensitivity * 0.1) * (this.orbitCamera.distance * 0.1);
        
        // Calculate pan difference
        this.calcMidPoint(touches[0], touches[1], pinchMidPoint);
        this.pan(pinchMidPoint);
        this.lastPinchMidPoint.copy(pinchMidPoint);
    }
};


// mouse-input.js
var MouseInput = pc.createScript('mouseInput');

MouseInput.attributes.add('orbitSensitivity', {
    type: 'number',
    default: 0.3,
    title: 'Orbit Sensitivity',
    description: 'How fast the camera moves around the orbit. Higher is faster'
});

MouseInput.attributes.add('distanceSensitivity', {
    type: 'number',
    default: 0.15,
    title: 'Distance Sensitivity',
    description: 'How fast the camera moves in and out. Higher is faster'
});
MouseInput.attributes.add('rightMouse', { type: 'boolean', title: '우클릭 화면 이동' });
MouseInput.attributes.add('ctrlZoom', { type: 'boolean', title: 'Ctrl키를 통해 줌 제어', default: true });

// initialize code called once per entity
MouseInput.prototype.initialize = function () {
    this.rotCheck = this.app.root.findByName('test Screen')?.script.testButton;
    this.orbitCamera = this.entity.script.orbitCamera;
    this.ctrlTemp = false;
    if (this.orbitCamera) {
        var self = this;

        var onMouseOut = function (e) {
            self.onMouseOut(e);
        };

        this.app.mouse.on(pc.EVENT_MOUSEDOWN, this.onMouseDown, this);
        this.app.mouse.on(pc.EVENT_MOUSEUP, this.onMouseUp, this);
        this.app.mouse.on(pc.EVENT_MOUSEMOVE, this.onMouseMove, this);
        this.app.mouse.on(pc.EVENT_MOUSEWHEEL, this.onMouseWheel, this);

        // Listen to when the mouse travels out of the window
        window.addEventListener('mouseout', onMouseOut, false);

        // Remove the listeners so if this entity is destroyed
        this.on('destroy', function () {
            this.app.mouse.off(pc.EVENT_MOUSEDOWN, this.onMouseDown, this);
            this.app.mouse.off(pc.EVENT_MOUSEUP, this.onMouseUp, this);
            this.app.mouse.off(pc.EVENT_MOUSEMOVE, this.onMouseMove, this);
            this.app.mouse.off(pc.EVENT_MOUSEWHEEL, this.onMouseWheel, this);

            window.removeEventListener('mouseout', onMouseOut, false);
        });
    }

    // Disabling the context menu stops the browser displaying a menu when
    // you right-click the page
    this.app.mouse.disableContextMenu();

    this.lookButtonDown = false;
    this.panButtonDown = false;
    this.lastPoint = new pc.Vec2();
};


MouseInput.fromWorldPoint = new pc.Vec3();
MouseInput.toWorldPoint = new pc.Vec3();
MouseInput.worldDiff = new pc.Vec3();


MouseInput.prototype.pan = function (screenPoint) {
    var fromWorldPoint = MouseInput.fromWorldPoint;
    var toWorldPoint = MouseInput.toWorldPoint;
    var worldDiff = MouseInput.worldDiff;

    // For panning to work at any zoom level, we use screen point to world projection
    // to work out how far we need to pan the pivotEntity in world space 
    var camera = this.entity.camera;
    var distance = this.orbitCamera.distance;

    camera.screenToWorld(screenPoint.x, screenPoint.y, distance, fromWorldPoint);
    camera.screenToWorld(this.lastPoint.x, this.lastPoint.y, distance, toWorldPoint);

    worldDiff.sub2(toWorldPoint, fromWorldPoint);
    // console.log(this.orbitCamera.pivotPoint);
    if (this.orbitCamera.pivotPoint.x + worldDiff.x < 4 && this.orbitCamera.pivotPoint.x + worldDiff.x > 0
        && this.orbitCamera.pivotPoint.y + worldDiff.y < 2 && this.orbitCamera.pivotPoint.y + worldDiff.y > -2
        && this.orbitCamera.pivotPoint.z + worldDiff.z < 2 && this.orbitCamera.pivotPoint.z + worldDiff.z > -2) {
        this.orbitCamera.pivotPoint.add(worldDiff);
    }
};


MouseInput.prototype.onMouseDown = function (event) {
    switch (event.button) {
        case pc.MOUSEBUTTON_LEFT: {
            this.lookButtonDown = true;
        } break;

        case pc.MOUSEBUTTON_MIDDLE:
        case pc.MOUSEBUTTON_RIGHT: {
            this.panButtonDown = true;
        } break;
    }
};


MouseInput.prototype.onMouseUp = function (event) {
    switch (event.button) {
        case pc.MOUSEBUTTON_LEFT: {
            this.lookButtonDown = false;
        } break;

        case pc.MOUSEBUTTON_MIDDLE:
        case pc.MOUSEBUTTON_RIGHT: {
            this.panButtonDown = false;
        } break;
    }
};


MouseInput.prototype.onMouseMove = function (event) {
    var mouse = pc.app.mouse;
    //회전중이면 못움직이게
    if (this.rotCheck?.isRotation) return;

    if (this.lookButtonDown) {
        this.orbitCamera.pitch -= event.dy * this.orbitSensitivity;
        this.orbitCamera.yaw -= event.dx * this.orbitSensitivity;

    } else if (this.panButtonDown && this.rightMouse) {
        this.pan(event);
    }

    this.lastPoint.set(event.x, event.y);
};


MouseInput.prototype.onMouseWheel = function (event) {
    if (!this.ctrlTemp) return;
    this.orbitCamera.distance -= event.wheel * this.distanceSensitivity * (this.orbitCamera.distance * 0.1);
    event.event.preventDefault();
};


MouseInput.prototype.onMouseOut = function (event) {
    this.lookButtonDown = false;
    this.panButtonDown = false;
};

MouseInput.prototype.update = function (dt) {
    if (this.app.keyboard.isPressed(pc.KEY_CONTROL) || this.ctrlZoom == false) this.ctrlTemp = true;
    else this.ctrlTemp = false;
}

// occluder.js
var Occulder = pc.createScript('occluder');
Occulder.attributes.add('layerName', { type: 'string' });
Occulder.attributes.add('materialAsset', { type: 'asset', assetType: 'material' });

// initialize code called once per entity
Occulder.prototype.initialize = function() {
    /** @type {pc.StandardMaterial} */
    const material = this.materialAsset.resource;
    material.redWrite = false;
    material.greenWrite = false;
    material.blueWrite = false;
    material.alphaWrite = false;
    
    material.update();

    const layer = this.app.scene.layers.getLayerByName(this.layerName);

    /** @type {pc.RenderComponent[]} */
    const renders = this.entity.findComponents('render');
    for (let i = 0; i < renders.length; ++i) {
        const mi = renders[i].meshInstances;
        for(let j = 0; j < mi.length; ++j) {
            mi[j].material = material;
        }

        renders[i].layers = [layer.id];
    }
};

// update code called every frame
Occulder.prototype.update = function(dt) {

};

// swap method called for script hot-reloading
// inherit your script state here
// Occulder.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/

// ssao.js
// The implementation is based on the code in Filament Engine: https://github.com/google/filament
// specifically, shaders here: https://github.com/google/filament/tree/24b88219fa6148b8004f230b377f163e6f184d65/filament/src/materials/ssao

// --------------- POST EFFECT DEFINITION --------------- //
/**
 * @class
 * @name SSAOEffect
 * @classdesc Implements the SSAOEffect post processing effect.
 * @description Creates new instance of the post effect.
 * @augments PostEffect
 * @param {GraphicsDevice} graphicsDevice - The graphics device of the application.
 * @param {any} ssaoScript - The script using the effect.
 */
function SSAOEffect(graphicsDevice, ssaoScript) {
    pc.PostEffect.call(this, graphicsDevice);

    this.ssaoScript = ssaoScript;
    this.needsDepthBuffer = true;

    var vshader = [
        (graphicsDevice.webgl2) ? ("#version 300 es\n\n" + pc.shaderChunks.gles3VS) : "",
        graphicsDevice.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n",
        "attribute vec2 aPosition;",
        "",
        "varying vec2 vUv0;",
        "",
        "void main(void)",
        "{",
        "   vUv0 = (aPosition.xy + 1.0) * 0.5;",
        "   gl_Position = vec4(aPosition, 0.0, 1.0);",
        "}"
    ].join("\n");

    this.ssaoShader = new pc.Shader(graphicsDevice, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: vshader,
        fshader: [
            (graphicsDevice.webgl2) ? ("#version 300 es\n\n" + pc.shaderChunks.gles3PS) : "",
            graphicsDevice.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n",
            "precision " + graphicsDevice.precision + " float;",
            pc.shaderChunks.screenDepthPS,
            "",
            "varying vec2 vUv0;",
            "",
            "uniform sampler2D uColorBuffer;",
            "uniform vec4 uResolution;",
            "",
            "uniform float uAspect;",
            "",
            "#define saturate(x) clamp(x,0.0,1.0)",
            "",
            "// Largely based on 'Dominant Light Shadowing'",
            "// 'Lighting Technology of The Last of Us Part II' by Hawar Doghramachi, Naughty Dog, LLC",
            "",
            "const float kSSCTLog2LodRate = 3.0;",
            "",
            "highp float getWFromProjectionMatrix(const mat4 p, const vec3 v) {",
            "    // this essentially returns (p * vec4(v, 1.0)).w, but we make some assumptions",
            "    // this assumes a perspective projection",
            "    return -v.z;",
            "    // this assumes a perspective or ortho projection",
            "    // return p[2][3] * v.z + p[3][3];",
            "}",
            "",
            "highp float getViewSpaceZFromW(const mat4 p, const float w) {",
            "    // this assumes a perspective projection",
            "    return -w;",
            "    // this assumes a perspective or ortho projection",
            "   // return (w - p[3][3]) / p[2][3];",
            "}",
            "",
            "",
            "const float kLog2LodRate = 3.0;",
            "",
            "vec2 sq(const vec2 a) {",
            "    return a * a;",
            "}",
            "",
            "uniform float uInvFarPlane;",
            "",
            "vec2 pack(highp float depth) {",
            "// we need 16-bits of precision",
            "    highp float z = clamp(depth * uInvFarPlane, 0.0, 1.0);",
            "    highp float t = floor(256.0 * z);",
            "    mediump float hi = t * (1.0 / 256.0);   // we only need 8-bits of precision",
            "    mediump float lo = (256.0 * z) - t;     // we only need 8-bits of precision",
            "    return vec2(hi, lo);",
            "}",
            "",
            "// random number between 0 and 1, using interleaved gradient noise",
            "float random(const highp vec2 w) {",
            "    const vec3 m = vec3(0.06711056, 0.00583715, 52.9829189);",
            "    return fract(m.z * fract(dot(w, m.xy)));",
            "}",
            "",
            "// returns the frag coord in the GL convention with (0, 0) at the bottom-left",
            "highp vec2 getFragCoord() {",
            "    return gl_FragCoord.xy;",
            "}",
            "",
            "highp vec3 computeViewSpacePositionFromDepth(highp vec2 uv, highp float linearDepth) {",
            "    return vec3((0.5 - uv) * vec2(uAspect, 1.0) * linearDepth, linearDepth);",
            "}",
            "",
            "highp vec3 faceNormal(highp vec3 dpdx, highp vec3 dpdy) {",
            "    return normalize(cross(dpdx, dpdy));",
            "}",
            "",
            "// Compute normals using derivatives, which essentially results in half-resolution normals",
            "// this creates arifacts around geometry edges.",
            "// Note: when using the spirv optimizer, this results in much slower execution time because",
            "//       this whole expression is inlined in the AO loop below.",
            "highp vec3 computeViewSpaceNormal(const highp vec3 position) {",
            "    return faceNormal(dFdx(position), dFdy(position));",
            "}",
            "",
            "// Compute normals directly from the depth texture, resulting in full resolution normals",
            "// Note: This is actually as cheap as using derivatives because the texture fetches",
            "//       are essentially equivalent to textureGather (which we don't have on ES3.0),",
            "//       and this is executed just once.",
            "highp vec3 computeViewSpaceNormal(const highp vec3 position, const highp vec2 uv) {",
            "    highp vec2 uvdx = uv + vec2(uResolution.z, 0.0);",
            "    highp vec2 uvdy = uv + vec2(0.0, uResolution.w);",
            "    highp vec3 px = computeViewSpacePositionFromDepth(uvdx, -getLinearScreenDepth(uvdx));",
            "    highp vec3 py = computeViewSpacePositionFromDepth(uvdy, -getLinearScreenDepth(uvdy));",
            "    highp vec3 dpdx = px - position;",
            "    highp vec3 dpdy = py - position;",
            "    return faceNormal(dpdx, dpdy);",
            "}",
            "",
            "// Ambient Occlusion, largely inspired from:",
            "// 'The Alchemy Screen-Space Ambient Obscurance Algorithm' by Morgan McGuire",
            "// 'Scalable Ambient Obscurance' by Morgan McGuire, Michael Mara and David Luebke",
            "",
            "uniform vec2 uSampleCount;",
            "uniform float uSpiralTurns;",
            "",
            "#define PI (3.14159)",
            "",
            "vec3 tapLocation(float i, const float noise) {",
            "    float offset = ((2.0 * PI) * 2.4) * noise;",
            "    float angle = ((i * uSampleCount.y) * uSpiralTurns) * (2.0 * PI) + offset;",
            "    float radius = (i + noise + 0.5) * uSampleCount.y;",
            "    return vec3(cos(angle), sin(angle), radius * radius);",
            "}",
            "",
            "highp vec2 startPosition(const float noise) {",
            "    float angle = ((2.0 * PI) * 2.4) * noise;",
            "    return vec2(cos(angle), sin(angle));",
            "}",
            "",
            "uniform vec2 uAngleIncCosSin;",
            "",
            "highp mat2 tapAngleStep() {",
            "    highp vec2 t = uAngleIncCosSin;",
            "    return mat2(t.x, t.y, -t.y, t.x);",
            "}",
            "",
            "vec3 tapLocationFast(float i, vec2 p, const float noise) {",
            "    float radius = (i + noise + 0.5) * uSampleCount.y;",
            "    return vec3(p, radius * radius);",
            "}",
            "",
            "uniform float uMaxLevel;",
            "uniform float uInvRadiusSquared;",
            "uniform float uMinHorizonAngleSineSquared;",
            "uniform float uBias;",
            "uniform float uPeak2;",
            "",
            "void computeAmbientOcclusionSAO(inout float occlusion, float i, float ssDiskRadius,",
            "        const highp vec2 uv,  const highp vec3 origin, const vec3 normal,",
            "        const vec2 tapPosition, const float noise) {",
            "",
            "    vec3 tap = tapLocationFast(i, tapPosition, noise);",
            "",
            "    float ssRadius = max(1.0, tap.z * ssDiskRadius);", // at least 1 pixel screen-space radius
            "",
            "    vec2 uvSamplePos = uv + vec2(ssRadius * tap.xy) * uResolution.zw;",
            "",
            "    float level = clamp(floor(log2(ssRadius)) - kLog2LodRate, 0.0, float(uMaxLevel));",
            "    highp float occlusionDepth = -getLinearScreenDepth(uvSamplePos);",
            "    highp vec3 p = computeViewSpacePositionFromDepth(uvSamplePos, occlusionDepth);",
            "",
            "    // now we have the sample, compute AO",
            "    vec3 v = p - origin;        // sample vector",
            "    float vv = dot(v, v);       // squared distance",
            "    float vn = dot(v, normal);  // distance * cos(v, normal)",
            "",
            "    // discard samples that are outside of the radius, preventing distant geometry to",
            "    // cast shadows -- there are many functions that work and choosing one is an artistic",
            "    // decision.",
            "    float w = max(0.0, 1.0 - vv * uInvRadiusSquared);",
            "    w = w*w;",
            "",
            "    // discard samples that are too close to the horizon to reduce shadows cast by geometry",
            "    // not sufficiently tessellated. The goal is to discard samples that form an angle 'beta'",
            "    // smaller than 'epsilon' with the horizon. We already have dot(v,n) which is equal to the",
            "    // sin(beta) * |v|. So the test simplifies to vn^2 < vv * sin(epsilon)^2.",
            "    w *= step(vv * uMinHorizonAngleSineSquared, vn * vn);",
            "",
            "    occlusion += w * max(0.0, vn + origin.z * uBias) / (vv + uPeak2);",
            "}",
            "",
            "uniform float uProjectionScaleRadius;",
            "uniform float uIntensity;",
            "",
            "float scalableAmbientObscurance(highp vec2 uv, highp vec3 origin, vec3 normal) {",
            "    float noise = random(getFragCoord());",
            "    highp vec2 tapPosition = startPosition(noise);",
            "    highp mat2 angleStep = tapAngleStep();",
            "",
            "    // Choose the screen-space sample radius",
            "    // proportional to the projected area of the sphere",
            "    float ssDiskRadius = -(uProjectionScaleRadius / origin.z);",
            "",
            "    float occlusion = 0.0;",

            // webgl1 does not handle non-constant loop, work around it
            graphicsDevice.webgl2 ? (
                "    for (float i = 0.0; i < uSampleCount.x; i += 1.0) {"
            ) : (
                "   const float maxSampleCount = 256.0;" +
                "   for (float i = 0.0; i < maxSampleCount; i += 1.0) {" +
                "       if (i >= uSampleCount.x) break;"
            ),

            "        computeAmbientOcclusionSAO(occlusion, i, ssDiskRadius, uv, origin, normal, tapPosition, noise);",
            "        tapPosition = angleStep * tapPosition;",
            "    }",
            "    return sqrt(occlusion * uIntensity);",
            "}",
            "",
            "uniform float uPower;",
            "",
            "void main() {",
            "    highp vec2 uv = vUv0; //variable_vertex.xy; // interpolated to pixel center",
            "",
            "    highp float depth = -getLinearScreenDepth(vUv0);",
            "    highp vec3 origin = computeViewSpacePositionFromDepth(uv, depth);",
            "    vec3 normal = computeViewSpaceNormal(origin, uv);",
            "",
            "    float occlusion = 0.0;",
            "",
            "    if (uIntensity > 0.0) {",
            "        occlusion = scalableAmbientObscurance(uv, origin, normal);",
            "    }",
            "",
            "    // occlusion to visibility",
            "    float aoVisibility = pow(saturate(1.0 - occlusion), uPower);",
            "",
            "    vec4 inCol = vec4(1.0, 1.0, 1.0, 1.0); //texture2D( uColorBuffer,  uv );",
            "",
            "    gl_FragColor.r = aoVisibility; //postProcess.color.rgb = vec3(aoVisibility, pack(origin.z));",
            "}",
            "",
            "void main_old()",
            "{",
            "    vec2 aspectCorrect = vec2( 1.0, uAspect );",
            "",
            "    float depth = getLinearScreenDepth(vUv0);",
            "    gl_FragColor.r = fract(floor(depth*256.0*256.0)),fract(floor(depth*256.0)),fract(depth);",
            "}"
        ].join("\n")
    });

    this.blurShader = new pc.Shader(graphicsDevice, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: vshader,
        fshader: [
            (graphicsDevice.webgl2) ? ("#version 300 es\n\n" + pc.shaderChunks.gles3PS) : "",
            graphicsDevice.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n",
            "precision " + graphicsDevice.precision + " float;",
            pc.shaderChunks.screenDepthPS,
            "",
            "varying vec2 vUv0;",
            "",
            "uniform sampler2D uColorBuffer;",
            "uniform sampler2D uSSAOBuffer;",
            "uniform vec4 uResolution;",
            "",
            "uniform float uAspect;",
            "",
            "uniform int uBilatSampleCount;",
            "uniform float uFarPlaneOverEdgeDistance;",
            "uniform float uBrightness;",
            "",
            "float random(const highp vec2 w) {",
            "    const vec3 m = vec3(0.06711056, 0.00583715, 52.9829189);",
            "    return fract(m.z * fract(dot(w, m.xy)));",
            "}",
            "",
            "float bilateralWeight(in float depth, in float sampleDepth) {",
            "    float diff = (sampleDepth - depth) * uFarPlaneOverEdgeDistance;",
            "    return max(0.0, 1.0 - diff * diff);",
            "}",
            "",
            "void tap(inout float sum, inout float totalWeight, float weight, float depth, vec2 position) {",
            "    // ambient occlusion sample",
            "    float ssao = texture2D( uSSAOBuffer, position ).r;",
            "    float tdepth = -getLinearScreenDepth( position );",
            "",
            "    // bilateral sample",
            "    float bilateral = bilateralWeight(depth, tdepth);",

            "    bilateral *= weight;",
            "    sum += ssao * bilateral;",
            "    totalWeight += bilateral;",
            "}",
            "",
            "void main() {",
            "    highp vec2 uv = vUv0; // variable_vertex.xy; // interpolated at pixel's center",
            "",
            "    // we handle the center pixel separately because it doesn't participate in bilateral filtering",
            "    float depth = -getLinearScreenDepth(vUv0); // unpack(data.gb);",
            "    float totalWeight = 0.0; // float(uBilatSampleCount*2+1)*float(uBilatSampleCount*2+1);",
            "    float ssao = texture2D( uSSAOBuffer, vUv0 ).r;",
            "    float sum = ssao * totalWeight;",
            "",

            // webgl1 does not handle non-constant loop, work around it
            graphicsDevice.webgl2 ? (
                "    for (int x = -uBilatSampleCount; x <= uBilatSampleCount; x++) {" +
                "       for (int y = -uBilatSampleCount; y < uBilatSampleCount; y++) {"
            ) : (
                "    for (int x = -4; x <= 4; x++) {" +
                "       for (int y = -4; y < 4; y++) {"
            ),

            "           float weight = 1.0;",
            "           vec2 offset = vec2(x,y)*uResolution.zw;",
            "           tap(sum, totalWeight, weight, depth, uv + offset);",
            "       }",
            "    }",
            "",
            "    float ao = sum / totalWeight;",
            "",
            "    // simple dithering helps a lot (assumes 8 bits target)",
            "    // this is most useful with high quality/large blurs",
            "    // ao += ((random(gl_FragCoord.xy) - 0.5) / 255.0);",
            "",
            "    ao = mix(ao, 1.0, uBrightness);",
            "    gl_FragColor.a = ao;",
            "}"
        ].join("\n")
    });

    this.outputShader = new pc.Shader(graphicsDevice, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: vshader,
        fshader: [
            (graphicsDevice.webgl2) ? ("#version 300 es\n\n" + pc.shaderChunks.gles3PS) : "",
            graphicsDevice.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n",
            "precision " + graphicsDevice.precision + " float;",
            "varying vec2 vUv0;",
            "uniform float uUpscale;",
            "uniform sampler2D uColorBuffer;",
            "uniform sampler2D uSSAOBuffer;",
            "",
            "void main(void)",
            "{",
            "    vec4 inCol = texture2D( uColorBuffer,  vUv0 );",
            "    float ssao = texture2D( uSSAOBuffer,  vUv0 ).a;",
            "    gl_FragColor.rgb = inCol.rgb * ssao;",
            "    gl_FragColor.a = inCol.a;",
            "}"
        ].join("\n")
    });

    // Uniforms
    this.radius = 4;
    this.brightness = 0;
    this.samples = 20;
    this.downscale = 1.0;

    this.resize(null);
}

SSAOEffect.prototype = Object.create(pc.PostEffect.prototype);
SSAOEffect.prototype.constructor = SSAOEffect;

SSAOEffect.prototype.resize = function (target) {

    var width, height;
    if (target === null) {
        width = Math.ceil(this.device.width / this.device.maxPixelRatio / this.downscale);
        height = Math.ceil(this.device.height / this.device.maxPixelRatio / this.downscale);
    } else {
        width = Math.ceil(target.colorBuffer.width / this.device.maxPixelRatio / this.downscale);
        height = Math.ceil(target.colorBuffer.height / this.device.maxPixelRatio / this.downscale);
    }

    // If no change, skip resize
    if (width === this.width && height === this.height)
        return;

    // Render targets
    this.width = width;
    this.height = height;
    if (this.target) {
        this.target.destroyFrameBuffers();
        this.target.destroyTextureBuffers();
        this.target.destroy();
        this.target = null;

        this.blurTarget.destroyFrameBuffers();
        this.blurTarget.destroyTextureBuffers();
        this.blurTarget.destroy();
        this.blurTarget = null;
    }
    var ssaoResultBuffer = new pc.Texture(this.device, {
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR,
        addressU: pc.ADDRESS_CLAMP_TO_EDGE,
        addressV: pc.ADDRESS_CLAMP_TO_EDGE,
        width: this.width,
        height: this.height,
        mipmaps: false
    });
    ssaoResultBuffer.name = 'ssao';
    this.target = new pc.RenderTarget({
        colorBuffer: ssaoResultBuffer,
        depth: false
    });

    var ssaoBlurBuffer = new pc.Texture(this.device, {
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR,
        addressU: pc.ADDRESS_CLAMP_TO_EDGE,
        addressV: pc.ADDRESS_CLAMP_TO_EDGE,
        width: this.width,
        height: this.height,
        mipmaps: false
    });
    this.blurTarget = new pc.RenderTarget({
        colorBuffer: ssaoBlurBuffer,
        depth: false
    });
};

Object.assign(SSAOEffect.prototype, {
    render: function (inputTarget, outputTarget, rect) {
        var device = this.device;
        var scope = device.scope;

        var sampleCount = this.samples;
        var spiralTurns = 10.0;
        var step = (1.0 / (sampleCount - 0.5)) * spiralTurns * 2.0 * 3.141;

        var radius = this.radius;
        var bias = 0.001;
        var peak = 0.1 * radius;
        var intensity = (peak * 2.0 * 3.141) * 0.125;
        var projectionScale = 0.5 * device.height;
        var cameraFarClip = this.ssaoScript.entity.camera.farClip;

        scope.resolve("uAspect").setValue(this.width / this.height);
        scope.resolve("uResolution").setValue([this.width, this.height, 1.0 / this.width, 1.0 / this.height]);
        scope.resolve("uBrightness").setValue(this.brightness);

        scope.resolve("uInvFarPlane").setValue(1.0 / cameraFarClip);
        scope.resolve("uSampleCount").setValue([sampleCount, 1.0 / sampleCount]);
        scope.resolve("uSpiralTurns").setValue(spiralTurns);
        scope.resolve("uAngleIncCosSin").setValue([Math.cos(step), Math.sin(step)]);
        scope.resolve("uMaxLevel").setValue(0.0);
        scope.resolve("uInvRadiusSquared").setValue(1.0 / (radius * radius));
        scope.resolve("uMinHorizonAngleSineSquared").setValue(0.0);
        scope.resolve("uBias").setValue(bias);
        scope.resolve("uPeak2").setValue(peak * peak);
        scope.resolve("uIntensity").setValue(intensity);
        scope.resolve("uPower").setValue(1.0);
        scope.resolve("uProjectionScaleRadius").setValue(projectionScale * radius);

        // Render SSAO
        pc.drawFullscreenQuad(device, this.target, this.vertexBuffer, this.ssaoShader, rect);

        scope.resolve("uSSAOBuffer").setValue(this.target.colorBuffer);

        // scope.resolve("uFarPlaneOverEdgeDistance").setValue(cameraFarClip / bilateralThreshold);
        scope.resolve("uFarPlaneOverEdgeDistance").setValue(1);

        scope.resolve("uBilatSampleCount").setValue(4);

        // Perform the blur
        pc.drawFullscreenQuad(device, this.blurTarget, this.vertexBuffer, this.blurShader, rect);

        // Finally output to screen
        scope.resolve("uUpscale").setValue(1.0 / this.downscale);
        scope.resolve("uSSAOBuffer").setValue(this.blurTarget.colorBuffer);
        scope.resolve("uColorBuffer").setValue(inputTarget.colorBuffer);
        pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.outputShader, rect);
    }
});

// ----------------- SCRIPT DEFINITION ------------------ //
var SSAO = pc.createScript('ssao');

SSAO.attributes.add('radius', {
    type: 'number',
    default: 4,
    min: 0,
    max: 20,
    title: 'Radius'
});

SSAO.attributes.add('brightness', {
    type: 'number',
    default: 0,
    min: 0,
    max: 1,
    title: 'Brightness'
});

SSAO.attributes.add('samples', {
    type: 'number',
    default: 16,
    min: 1,
    max: 256,
    title: 'Samples'
});

SSAO.attributes.add('downscale', {
    type: 'number',
    default: 1,
    min: 1,
    max: 4,
    title: "Downscale"
});

SSAO.prototype.initialize = function () {
    this.effect = new SSAOEffect(this.app.graphicsDevice, this);
    this.effect.radius = this.radius;
    this.effect.brightness = this.brightness;
    this.effect.samples = this.samples;
    this.effect.downscale = this.downscale;

    this.on('attr', function (name, value) {
        this.effect[name] = value;
    }, this);

    var queue = this.entity.camera.postEffects;
    queue.addEffect(this.effect);

    this.on('state', function (enabled) {
        if (enabled) {
            queue.addEffect(this.effect);
        } else {
            queue.removeEffect(this.effect);
        }
    });

    this.on('attr:downscale', () => {
        if (!this.enabled) return;
        this.effect.resize();
    });

    this.on('destroy', function () {
        queue.removeEffect(this.effect);
    });
};

// hueSaturation.js
// --------------- POST EFFECT DEFINITION --------------- //
/**
 * @class
 * @name HueSaturationEffect
 * @classdesc Allows hue and saturation adjustment of the input render target.
 * @description Creates new instance of the post effect.
 * @augments PostEffect
 * @param {GraphicsDevice} graphicsDevice - The graphics device of the application.
 * @property {number} hue Controls the hue. Ranges from -1 to 1 (-1 is 180 degrees in the negative direction, 0 no change, 1 is 180 degrees in the postitive direction).
 * @property {number} saturation Controls the saturation. Ranges from -1 to 1 (-1 is solid gray, 0 no change, 1 maximum saturation).
 */
function HueSaturationEffect(graphicsDevice) {
    pc.PostEffect.call(this, graphicsDevice);

    // Shader author: tapio / http://tapio.github.com/
    this.shader = new pc.Shader(graphicsDevice, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: [
            "attribute vec2 aPosition;",
            "",
            "varying vec2 vUv0;",
            "",
            "void main(void)",
            "{",
            "    gl_Position = vec4(aPosition, 0.0, 1.0);",
            "    vUv0 = (aPosition.xy + 1.0) * 0.5;",
            "}"
        ].join("\n"),
        fshader: [
            "precision " + graphicsDevice.precision + " float;",
            "",
            "uniform sampler2D uColorBuffer;",
            "uniform float uHue;",
            "uniform float uSaturation;",
            "",
            "varying vec2 vUv0;",
            "",
            "void main() {",
            "    gl_FragColor = texture2D( uColorBuffer, vUv0 );",
            "",
            // uHue
            "    float angle = uHue * 3.14159265;",
            "    float s = sin(angle), c = cos(angle);",
            "    vec3 weights = (vec3(2.0 * c, -sqrt(3.0) * s - c, sqrt(3.0) * s - c) + 1.0) / 3.0;",
            "    float len = length(gl_FragColor.rgb);",
            "    gl_FragColor.rgb = vec3(",
            "        dot(gl_FragColor.rgb, weights.xyz),",
            "        dot(gl_FragColor.rgb, weights.zxy),",
            "        dot(gl_FragColor.rgb, weights.yzx)",
            "    );",
            "",
            // uSaturation
            "    float average = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3.0;",
            "    if (uSaturation > 0.0) {",
            "        gl_FragColor.rgb += (average - gl_FragColor.rgb) * (1.0 - 1.0 / (1.001 - uSaturation));",
            "    } else {",
            "        gl_FragColor.rgb += (average - gl_FragColor.rgb) * (-uSaturation);",
            "    }",
            "}"
        ].join("\n")
    });

    // uniforms
    this.hue = 0;
    this.saturation = 0;
}

HueSaturationEffect.prototype = Object.create(pc.PostEffect.prototype);
HueSaturationEffect.prototype.constructor = HueSaturationEffect;

Object.assign(HueSaturationEffect.prototype, {
    render: function (inputTarget, outputTarget, rect) {
        var device = this.device;
        var scope = device.scope;

        scope.resolve("uHue").setValue(this.hue);
        scope.resolve("uSaturation").setValue(this.saturation);
        scope.resolve("uColorBuffer").setValue(inputTarget.colorBuffer);
        pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.shader, rect);
    }
});

// ----------------- SCRIPT DEFINITION ------------------ //
var HueSaturation = pc.createScript('hueSaturation');

HueSaturation.attributes.add('hue', {
    type: 'number',
    default: 0,
    min: -1,
    max: 1,
    title: 'Hue'
});

HueSaturation.attributes.add('saturation', {
    type: 'number',
    default: 0,
    min: -1,
    max: 1,
    title: 'Saturation'
});

HueSaturation.prototype.initialize = function () {
    this.effect = new HueSaturationEffect(this.app.graphicsDevice);
    this.effect.hue = this.hue;
    this.effect.saturation = this.saturation;

    this.on('attr', function (name, value) {
        this.effect[name] = value;
    }, this);

    var queue = this.entity.camera.postEffects;

    queue.addEffect(this.effect);

    this.on('state', function (enabled) {
        if (enabled) {
            queue.addEffect(this.effect);
        } else {
            queue.removeEffect(this.effect);
        }
    });

    this.on('destroy', function () {
        queue.removeEffect(this.effect);
    });
};

// PixelatePostEffect.js
//--------------- POST EFFECT DEFINITION------------------------//
pc.extend(pc, function () {
    // Constructor - Creates an instance of our post effect
    var PixelatePostEffect = function (graphicsDevice, vs, fs) {
        // this is the shader definition for our effect
        this.shader = new pc.Shader(graphicsDevice, {
            attributes: {
                aPosition: pc.SEMANTIC_POSITION
            },
            vshader: [
                "attribute vec2 aPosition;",
                "",
                "varying vec2 vUv0;",
                "",
                "void main(void)",
                "{",
                "    gl_Position = vec4(aPosition, 0.0, 1.0);",
                "    vUv0 = (aPosition.xy + 1.0) * 0.5;",
                "}"
            ].join("\n"),
            fshader: [
                "precision " + graphicsDevice.precision + " float;",
                "",
                "uniform vec2 uResolution;",
                "uniform float uPixelize;",
                "uniform sampler2D uColorBuffer;",
                "",
                "varying vec2 vUv0;",
                "",
                "void main() {",
                "    vec2 uv = gl_FragCoord.xy / uResolution.xy;",
                "    vec2 div = vec2(uResolution.x * uPixelize / uResolution.y, uPixelize);",
                "    uv = floor(uv * div)/div;",
                "    vec4 color = texture2D(uColorBuffer, uv);",
                "    gl_FragColor = color;",
                "}"
            ].join("\n")
        });

        this.resolution = new Float32Array(2);
        this.pixelize = 100;
    };

    // Our effect must derive from pc.PostEffect
    PixelatePostEffect = pc.inherits(PixelatePostEffect, pc.PostEffect);

    PixelatePostEffect.prototype = pc.extend(PixelatePostEffect.prototype, {
        // Every post effect must implement the render method which 
        // sets any parameters that the shader might require and 
        // also renders the effect on the screen
        render: function (inputTarget, outputTarget, rect) {
            var device = this.device;
            var scope = device.scope;

            // Set the input render target to the shader. This is the image rendered from our camera
            scope.resolve("uColorBuffer").setValue(inputTarget.colorBuffer);
            
            this.resolution[0] = device.width;
            this.resolution[1] = device.height;
            scope.resolve("uResolution").setValue(this.resolution);       
            
            scope.resolve("uPixelize").setValue(this.pixelize);
            
            // Draw a full screen quad on the output target. In this case the output target is the screen.
            // Drawing a full screen quad will run the shader that we defined above
            pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.shader, rect);
        }
    });

    return {
        PixelatePostEffect: PixelatePostEffect
    };
}());


//--------------- SCRIPT DEFINITION------------------------//
var PostEffectPixelate = pc.createScript('PostEffectPixelate');

PostEffectPixelate.attributes.add('pixelize', {
    type: 'number',
    min: 2,
    max: 500,
    step: 1,
    default: 100
});

// initialize code called once per entity
PostEffectPixelate.prototype.initialize = function() {
    var effect = new pc.PixelatePostEffect(this.app.graphicsDevice);
    
    // add the effect to the camera's postEffects queue
    var queue = this.entity.camera.postEffects;
    queue.addEffect(effect);
    
    // when the script is enabled add our effect to the camera's postEffects queue
    this.on('enable', function () {
        queue.addEffect(effect, false); 
    });
    
    // when the script is disabled remove our effect from the camera's postEffects queue
    this.on('disable', function () {
        queue.removeEffect(effect); 
    });
    
    this.on('attr:pixelize', function (value, prev) {
        effect.pixelize = value;
    });
};

// brightnessContrast.js
// --------------- POST EFFECT DEFINITION --------------- //
/**
 * @class
 * @name BrightnessContrastEffect
 * @classdesc Changes the brightness and contrast of the input render target.
 * @description Creates new instance of the post effect.
 * @augments PostEffect
 * @param {GraphicsDevice} graphicsDevice - The graphics device of the application.
 * @property {number} brightness Controls the brightness of the render target. Ranges from -1 to 1 (-1 is solid black, 0 no change, 1 solid white).
 * @property {number} contrast Controls the contrast of the render target. Ranges from -1 to 1 (-1 is solid gray, 0 no change, 1 maximum contrast).
 */
function BrightnessContrastEffect(graphicsDevice) {
    pc.PostEffect.call(this, graphicsDevice);

    // Shader author: tapio / http://tapio.github.com/
    this.shader = new pc.Shader(graphicsDevice, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: [
            "attribute vec2 aPosition;",
            "",
            "varying vec2 vUv0;",
            "",
            "void main(void)",
            "{",
            "    gl_Position = vec4(aPosition, 0.0, 1.0);",
            "    vUv0 = (aPosition.xy + 1.0) * 0.5;",
            "}"
        ].join("\n"),
        fshader: [
            "precision " + graphicsDevice.precision + " float;",
            "",
            "uniform sampler2D uColorBuffer;",
            "uniform float uBrightness;",
            "uniform float uContrast;",
            "",
            "varying vec2 vUv0;",
            "",
            "void main() {",
            "    gl_FragColor = texture2D( uColorBuffer, vUv0 );",
            "    gl_FragColor.rgb += uBrightness;",
            "",
            "    if (uContrast > 0.0) {",
            "        gl_FragColor.rgb = (gl_FragColor.rgb - 0.5) / (1.0 - uContrast) + 0.5;",
            "    } else {",
            "        gl_FragColor.rgb = (gl_FragColor.rgb - 0.5) * (1.0 + uContrast) + 0.5;",
            "    }",
            "}"
        ].join("\n")
    });

    // Uniforms
    this.brightness = 0;
    this.contrast = 0;
}

BrightnessContrastEffect.prototype = Object.create(pc.PostEffect.prototype);
BrightnessContrastEffect.prototype.constructor = BrightnessContrastEffect;

Object.assign(BrightnessContrastEffect.prototype, {
    render: function (inputTarget, outputTarget, rect) {
        var device = this.device;
        var scope = device.scope;

        scope.resolve("uBrightness").setValue(this.brightness);
        scope.resolve("uContrast").setValue(this.contrast);
        scope.resolve("uColorBuffer").setValue(inputTarget.colorBuffer);
        pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.shader, rect);
    }
});

// ----------------- SCRIPT DEFINITION ------------------ //
var BrightnessContrast = pc.createScript('brightnessContrast');

BrightnessContrast.attributes.add('brightness', {
    type: 'number',
    default: 0,
    min: -1,
    max: 1,
    title: 'Brightness'
});

BrightnessContrast.attributes.add('contrast', {
    type: 'number',
    default: 0,
    min: -1,
    max: 1,
    title: 'Contrast'
});

BrightnessContrast.prototype.initialize = function () {
    this.effect = new BrightnessContrastEffect(this.app.graphicsDevice);
    this.effect.brightness = this.brightness;
    this.effect.contrast = this.contrast;

    this.on('attr', function (name, value) {
        this.effect[name] = value;
    }, this);

    var queue = this.entity.camera.postEffects;

    queue.addEffect(this.effect);

    this.on('state', function (enabled) {
        if (enabled) {
            queue.addEffect(this.effect);
        } else {
            queue.removeEffect(this.effect);
        }
    });

    this.on('destroy', function () {
        queue.removeEffect(this.effect);
    });
};

// bloom.js
// --------------- POST EFFECT DEFINITION --------------- //
var SAMPLE_COUNT = 15;

function computeGaussian(n, theta) {
    return ((1.0 / Math.sqrt(2 * Math.PI * theta)) * Math.exp(-(n * n) / (2 * theta * theta)));
}

function calculateBlurValues(sampleWeights, sampleOffsets, dx, dy, blurAmount) {
    // Look up how many samples our gaussian blur effect supports.

    // Create temporary arrays for computing our filter settings.
    // The first sample always has a zero offset.
    sampleWeights[0] = computeGaussian(0, blurAmount);
    sampleOffsets[0] = 0;
    sampleOffsets[1] = 0;

    // Maintain a sum of all the weighting values.
    var totalWeights = sampleWeights[0];

    // Add pairs of additional sample taps, positioned
    // along a line in both directions from the center.
    var i, len;
    for (i = 0, len = Math.floor(SAMPLE_COUNT / 2); i < len; i++) {
        // Store weights for the positive and negative taps.
        var weight = computeGaussian(i + 1, blurAmount);
        sampleWeights[i * 2] = weight;
        sampleWeights[i * 2 + 1] = weight;
        totalWeights += weight * 2;

        // To get the maximum amount of blurring from a limited number of
        // pixel shader samples, we take advantage of the bilinear filtering
        // hardware inside the texture fetch unit. If we position our texture
        // coordinates exactly halfway between two texels, the filtering unit
        // will average them for us, giving two samples for the price of one.
        // This allows us to step in units of two texels per sample, rather
        // than just one at a time. The 1.5 offset kicks things off by
        // positioning us nicely in between two texels.
        var sampleOffset = i * 2 + 1.5;

        // Store texture coordinate offsets for the positive and negative taps.
        sampleOffsets[i * 4] = dx * sampleOffset;
        sampleOffsets[i * 4 + 1] = dy * sampleOffset;
        sampleOffsets[i * 4 + 2] = -dx * sampleOffset;
        sampleOffsets[i * 4 + 3] = -dy * sampleOffset;
    }

    // Normalize the list of sample weightings, so they will always sum to one.
    for (i = 0, len = sampleWeights.length; i < len; i++) {
        sampleWeights[i] /= totalWeights;
    }
}

/**
 * @class
 * @name BloomEffect
 * @classdesc Implements the BloomEffect post processing effect.
 * @description Creates new instance of the post effect.
 * @augments PostEffect
 * @param {GraphicsDevice} graphicsDevice - The graphics device of the application.
 * @property {number} bloomThreshold Only pixels brighter then this threshold will be processed. Ranges from 0 to 1.
 * @property {number} blurAmount Controls the amount of blurring.
 * @property {number} bloomIntensity The intensity of the effect.
 */
function BloomEffect(graphicsDevice) {
    pc.PostEffect.call(this, graphicsDevice);

    // Shaders
    var attributes = {
        aPosition: pc.SEMANTIC_POSITION
    };

    var passThroughVert = [
        "attribute vec2 aPosition;",
        "",
        "varying vec2 vUv0;",
        "",
        "void main(void)",
        "{",
        "    gl_Position = vec4(aPosition, 0.0, 1.0);",
        "    vUv0 = (aPosition + 1.0) * 0.5;",
        "}"
    ].join("\n");

    // Pixel shader extracts the brighter areas of an image.
    // This is the first step in applying a bloom postprocess.
    var bloomExtractFrag = [
        "precision " + graphicsDevice.precision + " float;",
        "",
        "varying vec2 vUv0;",
        "",
        "uniform sampler2D uBaseTexture;",
        "uniform float uBloomThreshold;",
        "uniform float uBlueThreshold;",

        "uniform bool uRedBloom;",
        "uniform bool uGreenBloom;",
        "uniform bool uBlurBloom;",
        "",
        "void main(void)",
        "{",
                // Look up the original image color.
        "    vec4 color = texture2D(uBaseTexture, vUv0);",
        "",
                // Adjust it to keep only values brighter than the specified threshold.
        "    float bDistanceR = 0.0;",
        "    float bDistanceG = 0.0;",
        "    float bDistanceB = 0.0;",

        "    if(uRedBloom) bDistanceR = ((color.r - color.g) + (color.r - color.b)) * 0.5 ;",
        "    if(uGreenBloom) bDistanceG = ((color.g - color.r) + (color.g - color.b)) * 0.5 ;",
        "    if(uBlurBloom) bDistanceB = ((color.b - color.r) + (color.b - color.g)) * 0.5 ;",

        "    float bDistance = (bDistanceR + bDistanceG + bDistanceB) * 0.5;",
        "    float bThreshold = bDistance >= uBlueThreshold ? uBloomThreshold : 1.0;",
        "    gl_FragColor = clamp((color - bThreshold) / (1.0 - bThreshold), 0.0, 1.0);",
        "}"
    ].join("\n");

    // Pixel shader applies a one dimensional gaussian blur filter.
    // This is used twice by the bloom postprocess, first to
    // blur horizontally, and then again to blur vertically.
    var gaussianBlurFrag = [
        "precision " + graphicsDevice.precision + " float;",
        "",
        "#define SAMPLE_COUNT " + SAMPLE_COUNT,
        "",
        "varying vec2 vUv0;",
        "",
        "uniform sampler2D uBloomTexture;",
        "uniform vec2 uBlurOffsets[SAMPLE_COUNT];",
        "uniform float uBlurWeights[SAMPLE_COUNT];",
        "",
        "void main(void)",
        "{",
        "    vec4 color = vec4(0.0);",
                // Combine a number of weighted image filter taps.
        "    for (int i = 0; i < SAMPLE_COUNT; i++)",
        "    {",
        "        color += texture2D(uBloomTexture, vUv0 + uBlurOffsets[i]) * uBlurWeights[i];",
        "    }",
        "",
        "    gl_FragColor = color;",
        "}"
    ].join("\n");

    // Pixel shader combines the bloom image with the original
    // scene, using tweakable intensity levels.
    // This is the final step in applying a bloom postprocess.
    var bloomCombineFrag = [
        "precision " + graphicsDevice.precision + " float;",
        "",
        "varying vec2 vUv0;",
        "",
        "uniform float uBloomEffectIntensity;",
        "uniform sampler2D uBaseTexture;",
        "uniform sampler2D uBloomTexture;",
        "",
        "void main(void)",
        "{",
                // Look up the bloom and original base image colors.
        "    vec4 bloom = texture2D(uBloomTexture, vUv0) * uBloomEffectIntensity;",
        "    vec4 base = texture2D(uBaseTexture, vUv0);",
        "",
                // Darken down the base image in areas where there is a lot of bloom,
                // to prevent things looking excessively burned-out.
        "    base *= (1.0 - clamp(bloom, 0.0, 1.0));",
        "",
                // Combine the two images.
        "    gl_FragColor = base + bloom;",
        //"    gl_FragColor = bloom;",
        "}"
    ].join("\n");

    this.extractShader = new pc.Shader(graphicsDevice, {
        attributes: attributes,
        vshader: passThroughVert,
        fshader: bloomExtractFrag
    });
    this.blurShader = new pc.Shader(graphicsDevice, {
        attributes: attributes,
        vshader: passThroughVert,
        fshader: gaussianBlurFrag
    });
    this.combineShader = new pc.Shader(graphicsDevice, {
        attributes: attributes,
        vshader: passThroughVert,
        fshader: bloomCombineFrag
    });

    this.targets = [];

    // Effect defaults
    this.bloomThreshold = 0.25;
    this.blurAmount = 4;
    this.bloomIntensity = 1.25;

    // Uniforms
    this.sampleWeights = new Float32Array(SAMPLE_COUNT);
    this.sampleOffsets = new Float32Array(SAMPLE_COUNT * 2);
}

BloomEffect.prototype = Object.create(pc.PostEffect.prototype);
BloomEffect.prototype.constructor = BloomEffect;

BloomEffect.prototype._destroy = function () {
    if (this.targets) {
        var i;
        for (i = 0; i < this.targets.length; i++) {
            this.targets[i].destroyTextureBuffers();
            this.targets[i].destroy();
        }
    }
    this.targets.length = 0;
};

BloomEffect.prototype._resize = function (target) {


    var width = target.colorBuffer.width;
    var height = target.colorBuffer.height;

    if (width === this.width && height === this.height)
        return;

    this.width = width;
    this.height = height;

    this._destroy();

    // Render targets
    var i;
    for (i = 0; i < 2; i++) {
        var colorBuffer = new pc.Texture(this.device, {
            name: "Bloom Texture" + i,
            format: pc.PIXELFORMAT_R8_G8_B8_A8,
            width: width >> 1,
            height: height >> 1,
            mipmaps: false
        });
        colorBuffer.minFilter = pc.FILTER_LINEAR;
        colorBuffer.magFilter = pc.FILTER_LINEAR;
        colorBuffer.addressU = pc.ADDRESS_CLAMP_TO_EDGE;
        colorBuffer.addressV = pc.ADDRESS_CLAMP_TO_EDGE;
        colorBuffer.name = 'pe-bloom';
        var bloomTarget = new pc.RenderTarget({
            name: "Bloom Render Target " + i,
            colorBuffer: colorBuffer,
            depth: false
        });

        this.targets.push(bloomTarget);
    }
};

Object.assign(BloomEffect.prototype, {
    render: function (inputTarget, outputTarget, rect) {

        this._resize(inputTarget);

        var device = this.device;
        var scope = device.scope;

        // Pass 1: draw the scene into rendertarget 1, using a
        // shader that extracts only the brightest parts of the image.
        scope.resolve("uBloomThreshold").setValue(this.bloomThreshold);
        scope.resolve("uBlueThreshold").setValue(this.blueThreshold);

        scope.resolve("uRedBloom").setValue(this.redBloom);
        scope.resolve("uGreenBloom").setValue(this.greenBloom);
        scope.resolve("uBlurBloom").setValue(this.blurBloom);
        
        scope.resolve("uBaseTexture").setValue(inputTarget.colorBuffer);
        pc.drawFullscreenQuad(device, this.targets[0], this.vertexBuffer, this.extractShader);

        // Pass 2: draw from rendertarget 1 into rendertarget 2,
        // using a shader to apply a horizontal gaussian blur filter.
        calculateBlurValues(this.sampleWeights, this.sampleOffsets, 1.0 / this.targets[1].width, 0, this.blurAmount);
        scope.resolve("uBlurWeights[0]").setValue(this.sampleWeights);
        scope.resolve("uBlurOffsets[0]").setValue(this.sampleOffsets);
        scope.resolve("uBloomTexture").setValue(this.targets[0].colorBuffer);
        pc.drawFullscreenQuad(device, this.targets[1], this.vertexBuffer, this.blurShader);

        // Pass 3: draw from rendertarget 2 back into rendertarget 1,
        // using a shader to apply a vertical gaussian blur filter.
        calculateBlurValues(this.sampleWeights, this.sampleOffsets, 0, 1.0 / this.targets[0].height, this.blurAmount);
        scope.resolve("uBlurWeights[0]").setValue(this.sampleWeights);
        scope.resolve("uBlurOffsets[0]").setValue(this.sampleOffsets);
        scope.resolve("uBloomTexture").setValue(this.targets[1].colorBuffer);
        pc.drawFullscreenQuad(device, this.targets[0], this.vertexBuffer, this.blurShader);

        // Pass 4: draw both rendertarget 1 and the original scene
        // image back into the main backbuffer, using a shader that
        // combines them to produce the final bloomed result.
        scope.resolve("uBloomEffectIntensity").setValue(this.bloomIntensity);
        scope.resolve("uBloomTexture").setValue(this.targets[0].colorBuffer);
        scope.resolve("uBaseTexture").setValue(inputTarget.colorBuffer);
        pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.combineShader, rect);
    }
});

// ----------------- SCRIPT DEFINITION ------------------ //
var Bloom = pc.createScript('bloom');

Bloom.attributes.add('bloomIntensity', {
    type: 'number',
    default: 1,
    min: 0,
    title: 'Intensity'
});

Bloom.attributes.add('bloomThreshold', {
    type: 'number',
    default: 0.25,
    min: 0,
    max: 1,
    title: 'Bloom Threshold'
});

Bloom.attributes.add('blueThreshold', {
    type: 'number',
    default: 0.25,
    min: 0,
    max: 1,
    title: 'Blue Threshold'
});

Bloom.attributes.add('blurAmount', {
    type: 'number',
    default: 4,
    min: 1,
    'title': 'Blur amount'
});

Bloom.attributes.add('redBloom', {
    type: 'boolean',
    'title': 'Red Bloom'
});

Bloom.attributes.add('greenBloom', {
    type: 'boolean',
    'title': 'Green Bloom'
});

Bloom.attributes.add('blurBloom', {
    type: 'boolean',
    'title': 'Blur Bloom'
});

Bloom.prototype.initialize = function () {
    this.effect = new BloomEffect(this.app.graphicsDevice);

    this.effect.bloomThreshold = this.bloomThreshold;
    this.effect.blurAmount = this.blurAmount;
    this.effect.bloomIntensity = this.bloomIntensity;
    this.effect.blueThreshold = this.blueThreshold;

    this.effect.redBloom = this.redBloom;
    this.effect.greenBloom = this.greenBloom;
    this.effect.blurBloom = this.blurBloom;

    var queue = this.entity.camera.postEffects;

    queue.addEffect(this.effect);

    this.on('attr', function (name, value) {
        this.effect[name] = value;
    }, this);

    this.on('state', function (enabled) {
        if (enabled) {
            queue.addEffect(this.effect);
        } else {
            queue.removeEffect(this.effect);
        }
    });

    this.on('destroy', function () {
        queue.removeEffect(this.effect);
        this._destroy();
    });
};

// test_button.js
var TestButton = pc.createScript('testButton');
TestButton.attributes.add('maxPage', { type: 'number', title: "최대 페이지" });

// 외부 접근용 함수
let __this;
function playcnavas_func(func) {
    if (__this) {
        // console.log(func);
        func(__this);
    } else {
        console.log('defind playcanvas');
    }
}

function playcnavas_Interactive_Move(value) {
    if (__this) {
        __this.app.root.findByName('test Screen').script.testButton.moveNumber(value);
    } else {
        console.log('defind playcanvas');
    }
}
function playcnavas_Interactive_Rotation(value) {
    if (__this) {
        __this.app.root.findByName('test Screen').script.testButton.moveRotation(value);
    } else {
        console.log('defind playcanvas');
    }
}

// initialize code called once per entity
TestButton.prototype.initialize = function () {
    __this = this;
    //현재 페이지 기본값 1
    this.nowPageNumber = 1;

    //회전중인지 ?
    this.isRotation = false;
    //뒷면인지 ?
    this.isBack = false;

    this.nowPageEntity = this.app.root.findByName('p1');
    this.nowPageTitle = this.app.root.findByName('Page1 Title');

    this.scene = this.app.root.findByName('Scene_rotate');
    this.sceneAni = this.scene.anim;
    // this.sceneAni.resetTrigger('rot1');

    this.app.root.findByName('left_Button').button.on('click', function () {
        // playcnavas_Interactive_Rotation(false); return;
        if (!this.isRotation) {
            this.sceneAni.setTrigger('rot1');
            this.isRotation = true;
            this.pageMove(-1);
            //후방 체크
            this.isBack = !this.isBack;
        }
    }, this);

    this.app.root.findByName('right_Button').button.on('click', function () {
        // playcnavas_Interactive_Rotation(true); return;
        if (!this.isRotation) {
            this.sceneAni.setTrigger('rot1');
            this.isRotation = true;
            this.pageMove(1);
            //후방 체크
            this.isBack = !this.isBack;
        }
    }, this);

    this.app.root.findByName('back_Button').button.on('click', function () {
        this.app.root.findByName('Back Button Group').enabled = false;
        this.app.root.findByName('Button Group').enabled = true;
        this.app.root.findByName('Path Camera').enabled = false;
    }, this);

    this.sceneAni.on('end-animation-rot1', function () {
        // console.log("rot1 종료");
        this.pageMoveEnd();
    }, this);
    this.sceneAni.on('end-animation-rot2', function () {
        // console.log("rot2 종료");
        this.pageMoveEnd();
    }, this);
};

TestButton.prototype.pageMove = function (pageing) {
    this.nowPageNumber += pageing;
    if (this.nowPageNumber <= 0) {
        this.nowPageNumber = this.maxPage;
    } else if (this.nowPageNumber > this.maxPage) {
        this.nowPageNumber = 1;
    }

    console.log(this.nowPageTitle);
    this.nowPageTitle.enabled = false;
    this.nowPageTitle = this.app.root.findByName('Page' + this.nowPageNumber + ' Title');
    this.nowPageTitle.enabled = true;

    let pageEntity = this.app.root.findByName('p' + this.nowPageNumber); // 다음에 보여줄 페이지 엔티티
    let point = this.isBack ? this.app.root.findByName('Point_1') : this.app.root.findByName('Point_2'); // 뒤면 포인트 2 앞이면 포인트 1
    pageEntity.enabled = true;
    pageEntity.setLocalPosition(point.getLocalPosition());
    pageEntity.setLocalEulerAngles(point.getLocalEulerAngles());
    // console.log(pageEntity.enabled , point?.name , this.nowPageNumber);
};

// TestButton.prototype.

TestButton.prototype.pageMoveEnd = function () {
    this.isRotation = false;
    this.nowPageEntity.enabled = false;
    this.nowPageEntity = this.app.root.findByName('p' + this.nowPageNumber);
};

TestButton.prototype.moveRotation = function (value) {
    if (this.isRotation) return;

    let num = value ? -1 : 1;

    this.sceneAni.setTrigger('rot1');
    this.isRotation = true;
    this.pageMove(num);
    //후방 체크
    this.isBack = !this.isBack;
};

TestButton.prototype.moveNumber = function (value) {
    if(this.nowPageNumber == value) return;
    if (this.isRotation) return;
    
    this.nowPageNumber = value;
    this.sceneAni.setTrigger('rot1');
    this.isRotation = true;
    this.pageMove(0);
    this.isBack = !this.isBack;
};

// update code called every frame
// TestButton.prototype.update = function (dt) {

// };

// swap method called for script hot-reloading
// inherit your script state here
// TestButton.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// http://developer.playcanvas.com/en/user-manual/scripting/

// loading-scrren.js
window.loadingEnd = false;

pc.script.createLoadingScreen(function (app) {
    var showSplash = function () {
        // splash wrapper
        var wrapper = document.createElement('div');
        wrapper.id = 'application-splash-wrapper';
        document.body.appendChild(wrapper);

        // splash
        var splash = document.createElement('div');
        splash.id = 'application-splash';
        wrapper.appendChild(splash);
        splash.style.display = 'none';

        var logo = document.createElement('img');
        //임시 이미지 주소
        logo.src = 'https://wolbong.uokdc.com/assets/test/logo_04.png';
        // logo.src = './image/logo_3.png';

        splash.appendChild(logo);
        logo.onload = function () {
            splash.style.display = 'block';
        };

        var container = document.createElement('div');
        container.id = 'progress-bar-container';
        splash.appendChild(container);

        var bar = document.createElement('div');
        bar.id = 'progress-bar';
        container.appendChild(bar);

    };

    var hideSplash = function () {
        var splash = document.getElementById('application-splash-wrapper');
        splash.parentElement.removeChild(splash);
        window.loadingEnd = true;
    };

    var setProgress = function (value) {
        var bar = document.getElementById('progress-bar');
        if (bar) {
            value = Math.min(1, Math.max(0, value));
            bar.style.width = value * 100 + '%';
        }
    };

    var createCss = function () {
        var css = [
            'body {',
            '    background-color: #171714;',
            '}',
            '',
            '#application-splash-wrapper {',
            '    position: absolute;',
            '    top: 0;',
            '    left: 0;',
            '    height: 100%;',
            '    width: 100%;',
            '    background-color: #171714;',
            '        display: flex;',
            '        align-items: center;',
            '        justify-content: center;',
            '}',
            '',
            '#application-splash {',
            '    position: initial;',
            '    top: calc(50% - 28px);',
            '    width: 264px;',
            '    left: calc(50% - 132px);',
            '}',
            '',
            '#application-splash img {',
            '    width: 100%;',
            '}',
            '',
            '#progress-bar-container {',
            '    margin: 20px auto 0 auto;',
            '    height: 2px;',
            '    width: 100%;',
            '    background-color: #1d292c;',
            '}',
            '',
            '#progress-bar {',
            '    width: 0%;',
            '    height: 100%;',
            '    background-color: #fff;',
            '}',
            '',
            '@media (max-width: 480px) {',
            '    #application-splash {',
            '        width: 170px;',
            '        left: calc(50% - 85px);',
            '    }',
            '}'
        ].join('\n');

        var style = document.createElement('style');
        style.type = 'text/css';
        if (style.styleSheet) {
            style.styleSheet.cssText = css;
        } else {
            style.appendChild(document.createTextNode(css));
        }

        document.head.appendChild(style);
    };

    createCss();
    showSplash();

    app.on('preload:end', function () {
        app.off('preload:progress');
    });
    app.on('preload:progress', setProgress);
    app.on('start', hideSplash);
});

// particleOn.js
var ParticleOn = pc.createScript('particleOn');
ParticleOn.attributes.add('name' , {type:'string'});

// initialize code called once per entity
ParticleOn.prototype.initialize = function() {
    // this.entity.collision.on('triggerenter' , this.test , this);
    this.entity.collision.on('triggerenter', this.onCollisionStart, this);
    this.entity.collision.on('triggerleave', this.onCollisionEnd, this);
};

ParticleOn.prototype.onCollisionStart = function(coll){
    const _this = this;
    // console.log(call.tags);
    if(coll.tags._list[0].includes('Particle')){
        this.particleInterval = setInterval(function(){
            _this.entity.findByName("Particle_"+Math.ceil(Math.random()*3)).particlesystem.reset();
            _this.entity.findByName("Particle_"+Math.ceil(Math.random()*3)).particlesystem.play();
        }, 250 + (100 * (Math.random() * 10)));
    }
};

ParticleOn.prototype.onCollisionEnd = function(){
    clearInterval(this.particleInterval);
};

// update code called every frame
ParticleOn.prototype.update = function(dt) {

};

// swap method called for script hot-reloading
// inherit your script state here
// ParticleOn.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/

// interactionVR.js
var InteractionVr = pc.createScript('interactionVr');

InteractionVr.attributes.add('contentEntity', { type: 'entity' });

// initialize code called once per entity
InteractionVr.prototype.initialize = function () {
    this.entity.button.on('click', function () {
        console.log("클릭");

        // this.app.root.findByName('Path Camera').script.cameraPath.flyingThrough = true;
        // this.app.root.findByName('Path Camera').enabled = true;
        this.content();
    }, this);
    if (this.contentEntity.name == "interaction_Content_2") {
        this.contentEntity.findByName('YES_Btn').button.on('click', function () {
            this.test();
        }, this);
    }
};

// update code called every frame
InteractionVr.prototype.update = function (dt) {

};

InteractionVr.prototype.content = function () {
    const _this = this;
    let _content = this.contentEntity;
    _content.enabled = true;
    clearTimeout(this.contestEnabled);
    this.contestEnabled = setTimeout(function () {
        _content.enabled = false;
    }, 1000);
};


InteractionVr.prototype.test = function () {
    this.app.root.findByName('Back Button Group').enabled = true;
    this.app.root.findByName('Button Group').enabled = false;

    this.app.root.findByName("Path Point(Cam)").setPosition(this.app.root.findByName('Camera').getPosition());
    this.app.root.findByName("Path Point(Cam)").setEulerAngles(this.app.root.findByName('Camera').getEulerAngles());
    this.app.root.findByName("Path Point(End)").setEulerAngles(this.app.root.findByName('Camera').getEulerAngles());
    this.app.root.findByName('Camera').script.cameraPath.createPath();
    this.app.root.findByName('Camera').script.cameraPath.time = 0;
    this.app.root.findByName('Camera').script.cameraPath.flyingThrough = true;
    // this.app.root.findByName('Path Camera').enabled = true;
    this.app.root.findByName('Camera').script.mouseInput.enabled = false;
};
// swap method called for script hot-reloading
// inherit your script state here
// InteractionVr.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// http://developer.playcanvas.com/en/user-manual/scripting/

// lookAtCam.js
var LookAtcam = pc.createScript('lookAtcam');


LookAtcam.prototype.initialize = function() {
    this.camera = this.app.root.findByName('Camera Parent').findByName('Camera');
};

LookAtcam.prototype.update = function(dt) {
    this.entity.lookAt(this.camera.getPosition());
};
// swap method called for script hot-reloading
// inherit your script state here
// LookAtcam.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// http://developer.playcanvas.com/en/user-manual/scripting/

// camera-path.js
var CameraPath = pc.createScript('cameraPath');

// CameraPath.attributes.add('html', {type: 'asset', assetType:'html', title: 'HTML Asset'});
// CameraPath.attributes.add('css', {type: 'asset', assetType:'css', title: 'CSS Asset'});

CameraPath.attributes.add("pathRoot", {type: "entity", title: "Path Root"});
CameraPath.attributes.add("duration", {type: "number", default: 10, title: "Duration Secs"});

CameraPath.attributes.add("startTime", {
    type: "number", 
    default: 0, 
    title: "Start Time (Secs)", 
    description: "Start the path from a specific point in time"
});

// initialize code called once per entity
CameraPath.prototype.initialize = function() {
    // Generate the camera path using pc.Curve: http://developer.playcanvas.com/en/api/pc.Curve.html
    this.createPath();
    
    // Add the UI (see sample for using HTML: https://developer.playcanvas.com/en/tutorials/htmlcss-ui/)
    // this.addUi();
    
    // Learn more about live attribute tweaking from this project: https://playcanvas.com/editor/scene/475560
    // If the user decides to change the path while the app is running, this allows for quicker iteration
    this.on("attr:pathRoot", function (value, prev) {
        if (value) {
            this.createPath();
            this.time = 0;    
        }
    });
    
    this.on("attr:time", function (value, prev) {
        this.time = pc.math.clamp(this.startTime, 0, this.duration);    
    });
    
    this.time = pc.math.clamp(this.startTime, 0, this.duration);    
    
    // Caching some Vec3 objects that will be used in the update loop continously 
    // so we don't keep triggering the garbage collector
    this.lookAt = new pc.Vec3();
    this.up = new pc.Vec3();
    
    this.flyingThrough = true; 

    this.speed = 0.01;
    this.minSpeed = 0.02;
    this.slowTime = 0.25;

    this.startTimer = 0;
};


// update code called every frame
CameraPath.prototype.update = function(dt) {

    var percent;
    
    if (this.flyingThrough) {
        this.time += dt * this.speed;
        
        //끝나면 실행
        // Loop the path flythrough animation indefinitely 
        if (this.time > this.duration) {
            let camRot = this.entity.getEulerAngles();
            // this.app.root.findByName('Camera').setPosition(this.entity.getPosition());
            // this.app.root.findByName('Camera').setEulerAngles(this.entity.getEulerAngles());
            // this.entity.enabled = false;
            // this.app.root.findByName('VR_Camera').enabled = true;
            // this.time -= this.duration;
            this.entity.script.mouseInput.enabled = true;
            this.entity.script.orbitCamera.enabled = true;
            this.entity.script.orbitCamera.setAngle(camRot);
            this.app.root.findByName('VR_Content').enabled = true;
        }
    
        // Work out how far we are in time we have progressed along the path
        percent = this.time / this.duration;

        //천천히 시작
        if(this.time < 1 && this.speed <= 1){
            this.speed += this.speed * dt * 2;
        }
    
        //마지막에 천천히 들어가기
        if(this.time > this.duration - this.slowTime && this.speed > this.minSpeed){
            this.speed = pc.math.lerp(this.speed ,  this.duration - this.time , 0.1);
        }
    }/*  else {
        percent = this.pathSlider.value / this.pathSlider.max;
    } */
    
    
    // Get the interpolated values for the position from the curves     
    this.entity.setPosition(this.px.value(percent), this.py.value(percent), this.pz.value(percent));
    
    // Get the interpolated values for the look at point from the curves 
    this.lookAt.set(this.tx.value(percent), this.ty.value(percent), this.tz.value(percent));
    
    // Get the interpolated values for the up vector from the curves     
    this.up.set(this.ux.value(percent), this.uy.value(percent), this.uz.value(percent));
    
    // Make the camera look at the interpolated target position with the correct
    // up direction to allow for camera roll and to avoid glimbal lock
    this.entity.lookAt(this.lookAt, this.up);
};


CameraPath.prototype.createPath = function () {
    var curveMode = pc.CURVE_CARDINAL;
    
    // Create curves for position
    this.px = new pc.Curve(); 
    this.px.type = curveMode;
    
    this.py = new pc.Curve(); 
    this.py.type = curveMode;    
    
    this.pz = new pc.Curve(); 
    this.pz.type = curveMode;
    
    // Create curves for target look at position
    this.tx = new pc.Curve();
    this.tx.type = curveMode;
    
    this.ty = new pc.Curve();
    this.ty.type = curveMode;
    
    this.tz = new pc.Curve();
    this.tz.type = curveMode;
    
    // Create curves for the 'up' vector for use with the lookAt function to 
    // allow for roll and avoid gimbal lock
    this.ux = new pc.Curve();
    this.ux.type = curveMode;
    
    this.uy = new pc.Curve();
    this.uy.type = curveMode;
    
    this.uz = new pc.Curve();
    this.uz.type = curveMode;
    
    var nodes = this.pathRoot.children;  
    
    // Get the total linear distance of the path (this isn't correct but gives a decent approximation in length)
    var pathLength = 0;
    
    // Store the distance from the start of the path for each path node
    var nodePathLength = [];
    
    // For use when calculating the distance between two nodes on the path
    var distanceBetween = new pc.Vec3();
    
    // Push 0 as we are starting our loop from 1 for ease
    nodePathLength.push(0);
    
    for (i = 1; i < nodes.length; i++) {
        var prevNode = nodes[i-1];
        var nextNode = nodes[i];
        
        // Work out the distance between the current node and the one before in the path
        distanceBetween.sub2(prevNode.getPosition(), nextNode.getPosition());
        pathLength += distanceBetween.length();
        
        nodePathLength.push(pathLength);
    }
        
    for (i = 0; i < nodes.length; i++) {
        // Calculate the time for the curve key based on the distance of the path to the node
        // and the total path length so the speed of the camera travel stays relatively
        // consistent throughout
        var t = nodePathLength[i] / pathLength;
        
        var node = nodes[i];
        
        var pos = node.getPosition();
        this.px.add(t, pos.x);
        this.py.add(t, pos.y);
        this.pz.add(t, pos.z);
        
        // Create and store a lookAt position based on the node position and the forward direction
        var lookAt = pos.clone().add(node.forward);
        this.tx.add(t, lookAt.x);
        this.ty.add(t, lookAt.y);
        this.tz.add(t, lookAt.z);
        
        var up = node.up;
        this.ux.add(t, up.x);
        this.uy.add(t, up.y);
        this.uz.add(t, up.z);
    }
};

CameraPath.prototype.addUi = function() {
    var self = this;
    
    var style = document.createElement('style');
    document.head.appendChild(style);
    style.innerHTML = this.css.resource || '';
    this.div = document.createElement('div');
    this.div.classList.add('container');
    this.div.innerHTML = this.html.resource || '';
    document.body.appendChild(this.div);
    
    // Keep references to the slider and button
    this.resumeFlythroughButton = this.div.querySelector('.button');
    this.pathSlider = this.div.querySelector('.slider');

    // Continue the flythrough on the button press 
    this.resumeFlythroughButton.addEventListener('click', function() {
        self.flyingThrough = true;
    });
    
    // Stop the flythrough when changing the slider
    this.pathSlider.addEventListener('input', function() {
        self.flyingThrough = false;
        self.time = (self.pathSlider.value / self.pathSlider.max) * self.duration;
    });
};

// click-to-highlight.js
var ClickToHighlight = pc.createScript('clickToHighlight');

// initialize code called once per entity
ClickToHighlight.prototype.initialize = function () {

    // --- variables
    this.cameraEntity = this.entity;
    this.previous = undefined;

    this.moveCheck = this.app.root.findByName('test Screen').script.testButton;
    this.mouseOn = false;

    // Add touch event only if touch is available
    if (this.app.touch) {
        this.app.touch.on(pc.EVENT_TOUCHSTART, this.touchStart, this);
    } else {

        // Add a mousedown event handler
        this.app.mouse.on(pc.EVENT_MOUSEMOVE, this.mouseDown, this);
        this.app.mouse.on(pc.EVENT_MOUSEDOWN, this.mouseClick, this);
        this.app.mouse.on(pc.EVENT_MOUSEUP, this.mouseUp, this);
    }
};

ClickToHighlight.prototype.mouseDown = function (e) {
    if (this.mouseOn) {
        this.doRaycast_Ground(e);
    } else {
        this.doRaycast(e);
    }
};

ClickToHighlight.prototype.touchStart = function (e) {
    // Only perform the raycast if there is one finger on the screen
    if (e.touches.length == 1) {
        this.doRaycast(e.touches[0]);
    }
    e.event.preventDefault();
};

ClickToHighlight.prototype.doRaycast = function (screenPosition) {
    // The vec3 to raycast from
    var from = this.cameraEntity.getPosition();
    // The vec3 to raycast to 
    var to = this.cameraEntity.camera.screenToWorld(screenPosition.x, screenPosition.y, this.cameraEntity.camera.farClip);

    // Raycast between the two points
    var result = this.app.systems.rigidbody.raycastFirst(from, to);

    // If there was a hit, store the entity
    if (result) {
        if (result.entity.tags.has('Ground')) {
            if(this.previous){
                this.app.fire('Ermis:objectOutline:remove', this.previous);

                this.previous = undefined;
            }
            return;
        }

        this.app.fire('Ermis:objectOutline:add', result.entity);

        if (this.previous && this.previous._guid !== result.entity._guid) {
            this.app.fire('Ermis:objectOutline:remove', this.previous);
        }

        this.previous = result.entity;


    } else {

        if (this.previous) {
            this.app.fire('Ermis:objectOutline:remove', this.previous);

            this.previous = undefined;
        }
    }


};

ClickToHighlight.prototype.mouseClick = function () {

    if (this.previous && this.previous.tags.has("moveStickman")) {
        this.moveCheck.isRotation = true;
        this.previous.collision.enabled = false;
        this.mouseOn = true;

        let particle = this.previous.parent.findByName('Particle_Confeti');
        if(particle){
            particle.particlesystem.reset();
            particle.particlesystem.play();
        }
    }
};

ClickToHighlight.prototype.mouseUp = function () {

    if (this.previous && this.previous.tags.has("moveStickman")) {
        this.moveCheck.isRotation = false;
        this.previous.collision.enabled = true;
        this.mouseOn = false;

        let particle = this.previous.parent.findByName('Particle_Confeti');
        if(particle){
            particle.particlesystem.reset();
            particle.particlesystem.play();
        }
    }

};

ClickToHighlight.prototype.doRaycast_Ground = function (screenPosition) {
    var from = this.cameraEntity.getPosition();
    // The vec3 to raycast to 
    var to = this.cameraEntity.camera.screenToWorld(screenPosition.x, screenPosition.y, this.cameraEntity.camera.farClip);

    // Raycast between the two points
    var result = this.app.systems.rigidbody.raycastAll(from, to);

    let gorund = undefined;
    for (let entity of result) {
        if (this.previous && entity.entity.tags.has('Ground')) {
            this.previous.parent.setPosition(entity.point);
        }
    }
};

// apply-outline.js
var ApplyOutline = pc.createScript('applyOutline');

ApplyOutline.attributes.add('color', { type: 'rgba' });
ApplyOutline.attributes.add("thickness", {
  type: "number",
  default: 1.0,
  min: 1.0,
  max: 10,
  precision: 0,
  title: "Thickness"
});

// initialize code called once per entity
ApplyOutline.prototype.initialize = function() {
  
    // --- variables
    this.vec = new pc.Vec3();
    
    // --- execute
    this.prepare();
    
    // --- events
    window.addEventListener("resize", this.onResize.bind(this) );
    
    this.app.on('Ermis:objectOutline:add', function(entity){

        if( entity && entity.render && entity.render.layers.indexOf(this.outlineLayer.id) === -1 ){
            
            var layers = entity.render.layers.slice();
            layers.push(this.outlineLayer.id);
            
            entity.render.layers = layers;
        }
        
    }, this);
    
    this.app.on('Ermis:objectOutline:remove', function(entity){
        if(!entity.render) return;
        var index = entity.render.layers.indexOf(this.outlineLayer.id);
        
        if( entity && entity.render && index > -1 ){
            
            var layers = entity.render.layers.slice();
            layers.splice(index, 1);
            
            entity.render.layers = layers;
        }        
        
    }, this);
};

ApplyOutline.prototype.prepare = function() {
    
    // create texture and render target for rendering into, including depth buffer
    this.texture = new pc.Texture(this.app.graphicsDevice, {
        width: this.app.graphicsDevice.width,
        height: this.app.graphicsDevice.height,
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        autoMipmap: true,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR
    });
    //this.renderTarget = new pc.RenderTarget(this.app.graphicsDevice, this.texture, { depth: true });
    this.renderTarget = new pc.RenderTarget({ 
        colorBuffer: this.texture,
        depth: true,
        samples: 8
    });

    // get layers
    this.worldLayer = this.app.scene.layers.getLayerByName("World");    
    this.outlineLayer = this.app.scene.layers.getLayerByName("OutlineLayer");    

    // set up layer to render to the render target
    this.outlineLayer.renderTarget = this.renderTarget;
    
    // Create outline camera, which renders entities in outline layer
    this.outlineCamera = new pc.Entity();
    this.outlineCamera.addComponent("camera", {
        clearColor: new pc.Color(0.0, 0.0, 0.0, 0.0),
        layers: [this.outlineLayer.id],
        fov: this.app.root.findByName('Camera').camera.fov
    });
    this.app.root.addChild(this.outlineCamera);    
    this.outlineCamera.camera.priority = 0;
    
    // instanciate outline post process effect
    this.outline = new pc.OutlineEffect(this.app.graphicsDevice, this.thickness);
    this.outline.color = new pc.Color(0, 0, 1, 1);
    this.outline.texture = this.texture;
    this.entity.camera.postEffects.addEffect(this.outline);
};

ApplyOutline.prototype.onResize = function(){

    this.entity.camera.postEffects.removeEffect(this.outline);

    this.app.scene.layers.remove(this.outlineLayer);
    
    this.texture.destroy();
    this.texture = new pc.Texture(this.app.graphicsDevice, {
        width: this.app.graphicsDevice.width,
        height: this.app.graphicsDevice.height,
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        autoMipmap: true,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR
    });
    this.renderTarget.destroy();
    this.renderTarget = new pc.RenderTarget(this.app.graphicsDevice, this.texture, { depth: true });
    this.outlineLayer.renderTarget = this.renderTarget;

    this.app.scene.layers.insert(this.outlineLayer, 0);

    this.outline.texture = this.texture;
    this.entity.camera.postEffects.addEffect(this.outline);
};

// update code called every frame
ApplyOutline.prototype.update = function(dt) {
        
    var transform = this.entity.getWorldTransform();

    this.outlineCamera.setPosition(transform.getTranslation());        
    this.outlineCamera.setEulerAngles(transform.getEulerAngles());
    this.outlineCamera.setLocalScale(transform.getScale());
    
    this.outlineCamera.camera.horizontalFov = this.app.graphicsDevice.width > this.app.graphicsDevice.height ? false : true;
    
    // update color
    this.outline.color.copy(this.color);
};

// posteffect-outline.js
// --------------- POST EFFECT DEFINITION --------------- //
Object.assign(pc, function () {

    /**
     * @class
     * @name pc.OutlineEffect
     * @classdesc Applies an outline effect on input render target
     * @description Creates new instance of the post effect.
     * @augments pc.PostEffect
     * @param {pc.GraphicsDevice} graphicsDevice - The graphics device of the application.
     * @property {pc.Texture} texture The outline texture to use.
     * @property {pc.Color} color The outline color.
     */
    var OutlineEffect = function (graphicsDevice, thickness) {
        pc.PostEffect.call(this, graphicsDevice);

        this.shader = new pc.Shader(graphicsDevice, {
            attributes: {
                aPosition: pc.SEMANTIC_POSITION
            },
            vshader: [
                "attribute vec2 aPosition;",
                "",
                "varying vec2 vUv0;",
                "",
                "void main(void)",
                "{",
                "    gl_Position = vec4(aPosition, 0.0, 1.0);",
                "    vUv0 = (aPosition.xy + 1.0) * 0.5;",
                "}"
            ].join("\n"),
            fshader: [
                "precision " + graphicsDevice.precision + " float;",
                "",
                "#define THICKNESS "+thickness.toFixed(0),
                "uniform float uWidth;",
                "uniform float uHeight;",
                "uniform vec4 uOutlineCol;",
                "uniform sampler2D uColorBuffer;",
                "uniform sampler2D uOutlineTex;",
                "",
                "varying vec2 vUv0;",
                "",
                "void main(void)",
                "{",
                "    vec4 texel1 = texture2D(uColorBuffer, vUv0);",
                "    float sample0 = texture2D(uOutlineTex, vUv0).a;",
                "    float outline = 0.0;",
                "    if (sample0==0.0)",
                "    {",
                "        for (int x=-THICKNESS;x<=THICKNESS;x++)",
                "        {",
                "            for (int y=-THICKNESS;y<=THICKNESS;y++)",
                "            {    ",
                "                float sample=texture2D(uOutlineTex, vUv0+vec2(float(x)/uWidth, float(y)/uHeight)).a;",
                "                if (sample>0.0)",
                "                {",
                "                    outline=1.0;",
                "                }",
                "            }",
                "        } ",
                "    }",
                "    gl_FragColor = mix(texel1, uOutlineCol, outline * uOutlineCol.a);",
                "}"
            ].join("\n")
        });

        // Uniforms
        this.color = new pc.Color(1, 1, 1, 1);
        this.texture = new pc.Texture(graphicsDevice);
        this.texture.name = 'pe-outline';
    };

    OutlineEffect.prototype = Object.create(pc.PostEffect.prototype);
    OutlineEffect.prototype.constructor = OutlineEffect;
    
    var colorArray = [0,0,0,0];

    Object.assign(OutlineEffect.prototype, {
        render: function (inputTarget, outputTarget, rect) {
            var device = this.device;
            var scope = device.scope;

            scope.resolve("uWidth").setValue(inputTarget.width);
            scope.resolve("uHeight").setValue(inputTarget.height);
            
            colorArray[0] = this.color.r;
            colorArray[1] = this.color.g;
            colorArray[2] = this.color.b;
            colorArray[3] = this.color.a;
            scope.resolve("uOutlineCol").setValue(colorArray);
            
            scope.resolve("uColorBuffer").setValue(inputTarget.colorBuffer);
            scope.resolve("uOutlineTex").setValue(this.texture);
            pc.drawFullscreenQuad(device, outputTarget, this.vertexBuffer, this.shader, rect);
        }
    });

    return {
        OutlineEffect: OutlineEffect
    };
}());

// ----------------- SCRIPT DEFINITION ------------------ //
var Outline = pc.createScript('outline');

Outline.attributes.add('color', {
    type: 'rgb',
    default: [0.5, 0.5, 0.5, 1],
    title: 'Color'
});

Outline.attributes.add('texture', {
    type: 'asset',
    assetType: 'texture',
    title: 'Texture'
});

Outline.prototype.initialize = function () {
    this.effect = new pc.OutlineEffect(this.app.graphicsDevice);
    this.effect.color = this.color;
    this.effect.texture = this.texture.resource;

    var queue = this.entity.camera.postEffects;

    queue.addEffect(this.effect);

    this.on('state', function (enabled) {
        if (enabled) {
            queue.addEffect(this.effect);
        } else {
            queue.removeEffect(this.effect);
        }
    });

    this.on('destroy', function () {
        queue.removeEffect(this.effect);
    });

    this.on('attr:color', function (value) {
        this.effect.color = value;
    }, this);

    this.on('attr:texture', function (value) {
        this.effect.texture = value ? value.resource : null;
    }, this);
};


// ribbon.js
var Ribbon = pc.createScript('ribbon');

Ribbon.attributes.add("lifetime", {type:"number", default:0.5});
Ribbon.attributes.add("xoffset", {type:"number", default:-0.8});
Ribbon.attributes.add("yoffset", {type:"number", default:1});
Ribbon.attributes.add("height", {type:"number", default:0.4});

var MAX_VERTICES = 600;//150;//600;
var VERTEX_SIZE = 4;

Ribbon.prototype.create = function (entity) {
//    this.entity = entity;

    this.timer = 0;

    // The node generating this ribbon
    this.node = null;
    // The generated ribbon vertices
    this.vertices = [];

    // Vertex array to receive ribbon vertices
    this.vertexData = new Float32Array(MAX_VERTICES * VERTEX_SIZE);

    this.entity.model = null;
};

Ribbon.prototype.initialize = function () {
    this.create();
    
    var shaderDefinition = {
        attributes: {
            aPositionAge: pc.SEMANTIC_POSITION
        },
        vshader: [
            "attribute vec4 aPositionAge;",
            "",
            "uniform mat4 matrix_viewProjection;",
            "uniform float trail_time;",
            "",
            "varying float vAge;",
            "",
            "void main(void)",
            "{",
            "    vAge = trail_time - aPositionAge.w;",
            "    gl_Position = matrix_viewProjection * vec4(aPositionAge.xyz, 1.0);",
            "}"
        ].join("\n"),
        fshader: [
            "precision mediump float;",
            "",
            "varying float vAge;",
            "",
            "uniform float trail_lifetime;",
            "",
            "vec3 rainbow(float x)",
            "{",
                    "float level = floor(x * 6.0);",
                    "float r = float(level <= 2.0) + float(level > 4.0) * 0.5;",
                    "float g = max(1.0 - abs(level - 2.0) * 0.5, 0.0);",
                    "float b = (1.0 - (level - 4.0) * 0.5) * float(level >= 4.0);",
                    "return vec3(r, g, b);",
            "}",
            "void main(void)",
            "{",
            "    gl_FragColor = vec4(rainbow(vAge / trail_lifetime), (1.0 - (vAge / trail_lifetime)) * 0.5);",
            "}"
        ].join("\n")
    };
    
//    var shader = new pc.Shader(context.graphicsDevice, shaderDefinition);
    var shader = new pc.Shader(this.app.graphicsDevice, shaderDefinition);

    var material = new pc.scene.Material();
    material.setShader(shader);
    material.setParameter('trail_time', 0);
    material.setParameter('trail_lifetime', this.lifetime);
    material.cull = pc.CULLFACE_NONE;
    material.blend = true;
    material.blendSrc = pc.BLENDMODE_SRC_ALPHA;
    material.blendDst = pc.BLENDMODE_ONE_MINUS_SRC_ALPHA;
    material.blendEquation = pc.BLENDEQUATION_ADD;
    material.depthWrite = false;

    // Create the vertex format
    var vertexFormat = new pc.VertexFormat(this.app.context.graphicsDevice, [
        { semantic: pc.SEMANTIC_POSITION, components: 4, type: pc.ELEMENTTYPE_FLOAT32 }
    ]);

    // Create a vertex buffer
    var vertexBuffer = new pc.VertexBuffer(this.app.context.graphicsDevice, vertexFormat, MAX_VERTICES * VERTEX_SIZE, pc.USAGE_DYNAMIC);

    var mesh = new pc.scene.Mesh();
    mesh.vertexBuffer = vertexBuffer;
    mesh.indexBuffer[0] = null;
    mesh.primitive[0].type = pc.PRIMITIVE_TRISTRIP;
    mesh.primitive[0].base = 0;
    mesh.primitive[0].count = 0;
    mesh.primitive[0].indexed = false;

    var node = new pc.scene.GraphNode();

    var meshInstance = new pc.scene.MeshInstance(node, mesh, material);
    meshInstance.layer = pc.scene.LAYER_WORLD;
    meshInstance.updateKey();

    this.entity.model = new pc.scene.Model();
    this.entity.model.graph = node;
    this.entity.model.meshInstances.push(meshInstance);

    this.model = this.entity.model;

    this.setNode(this.entity);
};


Ribbon.prototype.reset = function () {
    this.timer = 0;
    this.vertices = [];
};
        
Ribbon.prototype.spawn = function () {
    var node = this.node;
    var pos = node.getPosition();
    var yaxis = node.up.clone().scale(this.height);

    var s = this.xoffset;
    var e = this.yoffset;
    this.vertices.unshift({
        spawnTime: this.timer,
        vertexPair: [
            pos.x + yaxis.x * s, pos.y + yaxis.y * s, pos.z + yaxis.z * s, 
            pos.x + yaxis.x * e, pos.y + yaxis.y * e, pos.z + yaxis.z * e
        ]
    });
};
        
Ribbon.prototype.clearOld = function () {
    for (var i = this.vertices.length - 1; i >= 0; i--) {
        var vp = this.vertices[i];
        if (this.timer - vp.spawnTime >= this.lifetime) {
            this.vertices.pop();
        } else {
            return;
        }
    }
};
        
Ribbon.prototype.copyToArrayBuffer = function () {
    for (var i = 0; i < this.vertices.length; i++) {
        var vp = this.vertices[i];

        this.vertexData[i * 8 + 0] = vp.vertexPair[0];
        this.vertexData[i * 8 + 1] = vp.vertexPair[1];
        this.vertexData[i * 8 + 2] = vp.vertexPair[2];
        this.vertexData[i * 8 + 3] = vp.spawnTime;

        this.vertexData[i * 8 + 4] = vp.vertexPair[3];
        this.vertexData[i * 8 + 5] = vp.vertexPair[4];
        this.vertexData[i * 8 + 6] = vp.vertexPair[5];
        this.vertexData[i * 8 + 7] = vp.spawnTime;
    }
};
        
Ribbon.prototype.updateNumActive = function () {
    this.model.meshInstances[0].mesh.primitive[0].count = this.vertices.length * 2;
};

Ribbon.prototype.update = function (dt) {
    this.timer += dt;
    var material = this.model.meshInstances[0].material;
    material.setParameter('trail_time', this.timer);

    this.clearOld();
    this.spawn();

    if (this.vertices.length > 1) {
        this.copyToArrayBuffer();
        this.updateNumActive();

        var vertexBuffer = this.model.meshInstances[0].mesh.vertexBuffer;
        var vertices = new Float32Array(vertexBuffer.lock());
        vertices.set(this.vertexData);
        vertexBuffer.unlock();

        if (!this.app.scene.containsModel(this.model)) {
            console.log("Added model");
            this.app.scene.addModel(this.model);
        }
    } else {
        if (this.app.scene.containsModel(this.model)) {
            console.log("Removed model");
            this.app.scene.removeModel(this.model);
        }
    }
};

Ribbon.prototype.setNode = function (node) {
    this.node = node;
};


// cameraPositionSetting.js
let __this_cps;
// 외부에서 자세 , 시점 전환용 함수 ##_@@@@ 형태 ( ## : 자세 번호 / @@@@ : 시점  ex) 03_front 3자세의 정면)
window.call3DModelEvent = function (target) {
    __this_cps.characterSetting(target);
};

window.enabled_muscle = function (muscle, enabled, color) {
    return __this_cps.enabled_muscle(muscle, enabled, color);
};

window.enabled_center_point = function (enabled, color) {
    return __this_cps.enabled_center_point(enabled, color);
};

window.enabled_bucket = function (enabled) {
    return __this_cps.enabled_bucket(enabled);
};

window.enabled_stick = function (enabled) {
    return __this_cps.enabled_stick(enabled);
};

window.set_point_size = function (width, height) {
    return __this_cps.set_point_size(width, height);
};

window.add_point_position = function (x, y, z, reset) {
    return __this_cps.add_point_position(x, y, z, reset);
};

window.character_color_setting = function (value, color) {
    return __this_cps.character_color_setting(value, color);
}

var CameraPositionSetting = pc.createScript('cameraPositionSetting');
CameraPositionSetting.attributes.add('camera', { type: "entity", title: '카메라' });
CameraPositionSetting.attributes.add('cameraIcon', { type: "entity", title: '카메라아이콘' });
CameraPositionSetting.attributes.add('pitchAngle', { type: "number", title: 'pitch 가동범위', default: 10 });
CameraPositionSetting.attributes.add('yawAngle', { type: "number", title: 'yaw 가동 범위', default: 30 });

CameraPositionSetting.attributes.add('character', { type: 'entity', title: '캐릭터' });

CameraPositionSetting.attributes.add('zoom_reset_btn', { type: 'entity', title: '줌 초기화 버튼' });

CameraPositionSetting.attributes.add('toolEntity', {
    type: 'json',
    schema: [{
        name: 'entity',
        type: 'entity',
        title: '도구'
    }, {
        name: 'name',
        type: 'string',
        title: '이름',
    }
    ],
    array: true
});

CameraPositionSetting.attributes.add('muscle_colors', { type: "asset", array: true, title: "근육 색상 메터리얼" });
CameraPositionSetting.attributes.add('character_colors_defult', { type: "asset", array: true, title: "캐릭터 기본 색상 메터리얼" });
CameraPositionSetting.attributes.add('character_colors', { type: "asset", array: true, title: "캐릭터 변경 색상 메터리얼" });
// initialize code called once per entity




CameraPositionSetting.prototype.initialize = function () {
    __this_cps = this;
    this.orbitCam = this.camera.script.orbitCamera;
    this.orbitCam._targetDistance = 5;
    if (this.cameraIcon) this.camIcon = this.cameraIcon.script.iconSpin;
    if (this.character) this.ani = this.character.anim;
    if (this.zoom_reset_btn) this.zoom_reset_btn.button.on('click', this.camDistanceReset, this);
    this.nowPitch = 0;
    this.nowYaw = 0;
    this.setCameraAngle(this.nowPitch, this.nowYaw);

    // 테스트 구역
    this.character_model = this.app.root.findByName('MHuman');

    this.point = this.app.root.findByName('Point');
    this.point_image = this.app.root.findByName('Point_image');
    this.point_defult_pos = this.point.getPosition().clone();
    // this.warning_image_2 = this.app.root.findByName('Warning_image_2');
    // if(window.character_color)this.character_color_setting(window.character_color);
    this.createUI();

    add_point_position('reset');
};

// update code called every frame
CameraPositionSetting.prototype.update = function (dt) {
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_8)) {
        this.setCameraAngle(0, 0);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_4)) {
        this.setCameraAngle(0, -90);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_6)) {
        this.setCameraAngle(0, 90);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_5)) {
        this.setCameraAngle(0, 180);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_7)) {
        this.setCameraAngle(90, this.nowYaw);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_NUMPAD_9)) {
        this.setCameraAngle(-90, this.nowYaw);
    }

    if (this.app.keyboard.wasPressed(pc.KEY_1)) {
        this.ani?.setBoolean('pose1', false);
        this.ani?.setBoolean('pose2', false);
        this.toolEntity.find(e => e.name == 'basket').entity.enabled = false;
        this.toolEntity.find(e => e.name == 'stick').entity.enabled = false;
    }
    if (this.app.keyboard.wasPressed(pc.KEY_2)) {
        this.ani?.setBoolean('pose1', true);
        this.ani?.setBoolean('pose2', false);
        this.toolEntity.find(e => e.name == 'basket').entity.enabled = true;
        this.toolEntity.find(e => e.name == 'stick').entity.enabled = true;
    }
    if (this.app.keyboard.wasPressed(pc.KEY_3)) {
        this.ani?.setBoolean('pose1', false);
        this.ani?.setBoolean('pose2', true);
        this.toolEntity.find(e => e.name == 'basket').entity.enabled = true;
        this.toolEntity.find(e => e.name == 'stick').entity.enabled = true;
    }

    if (this.app.keyboard.wasPressed(pc.KEY_Q)) {
        this.toolEntity.find(e => e.name == 'basket').entity.enabled = true;
    }

    if (this.app.keyboard.wasPressed(pc.KEY_W)) {
        this.toolEntity.find(e => e.name == 'basket').entity.enabled = false;
    }

    if (this.app.keyboard.wasPressed(pc.KEY_E)) {
        this.toolEntity.find(e => e.name == 'stick').entity.enabled = true;
    }

    if (this.app.keyboard.wasPressed(pc.KEY_R)) {
        this.toolEntity.find(e => e.name == 'stick').entity.enabled = false;
    }


    //테스트구역
    let pos1 = this.camera.camera.worldToScreen(this.point.getPosition());
    this.point_image.setLocalPosition(pos1.x, -pos1.y, 0);
};

CameraPositionSetting.prototype.setCameraAngle = function (pitch, yaw) {
    this.orbitCam.pitchAngleMax = pitch + this.pitchAngle;
    this.orbitCam.pitchAngleMin = pitch - this.pitchAngle;
    this.orbitCam.yawAngleMax = yaw + this.yawAngle;
    this.orbitCam.yawAngleMin = yaw - this.yawAngle;
    this.orbitCam._targetPitch = -pitch;
    this.orbitCam._targetYaw = -yaw;

    if (this.cameraIcon) this.camIcon.setIconRot(pitch, yaw);

    this.nowPitch = pitch;
    this.nowYaw = yaw;
};

CameraPositionSetting.prototype.camDistanceReset = function () {
    this.orbitCam._targetDistance = 5;
};

CameraPositionSetting.prototype.characterSetting = function (target) {
    let _target = target.split('_');
    let target_pose = _target[0];
    let target_pos = _target[1];

    // 포즈 설정
    switch (target_pose) {
        case '00': {
            this.ani?.setBoolean('pose1', false);
            this.ani?.setBoolean('pose2', false);
            this.toolEntity.find(e => e.name == 'basket').entity.enabled = false;
            this.toolEntity.find(e => e.name == 'stick').entity.enabled = false;
        } break;

        case '02': {
            this.ani?.setBoolean('pose1', true);
            this.ani?.setBoolean('pose2', false);
            this.toolEntity.find(e => e.name == 'basket').entity.enabled = true;
            this.toolEntity.find(e => e.name == 'stick').entity.enabled = true;
        } break;

        case '03': {
            this.ani?.setBoolean('pose1', false);
            this.ani?.setBoolean('pose2', true);
            this.toolEntity.find(e => e.name == 'basket').entity.enabled = true;
            this.toolEntity.find(e => e.name == 'stick').entity.enabled = true;
        } break;
    }
    // 카메라 위치제어
    switch (target_pos) {
        case 'front': {
            this.setCameraAngle(0, 0);
        } break;
        case 'back': {
            this.setCameraAngle(0, 180);
        } break;
        case 'side': {
            this.setCameraAngle(0, -90);
        } break;
        case 'top': {
            this.setCameraAngle(90, this.nowYaw);
        } break;
    }
};

CameraPositionSetting.prototype.enabled_muscle = function (muscle, enabled, color) {
    if (!this.character) return "캐릭터 부재";
    let target = this.character.findByName(muscle);
    target.enabled = enabled;
    if (!enabled) return;
    if (!this.muscle_colors[color - 1]) return "해당하는 색상이 없음";
    target.render.material = this.muscle_colors[color - 1].resources[0];
}

CameraPositionSetting.prototype.enabled_center_point = function (enabled/* , color */) {
    if (this.point_image) {
        this.point_image.enabled = enabled;
    }
    // if(color){
    //     this.warning_image_1.element.color = 
    // }
};
CameraPositionSetting.prototype.enabled_bucket = function (enabled/* , color */) {
    this.toolEntity.find(e => e.name == 'basket').entity.enabled = enabled;
};

CameraPositionSetting.prototype.enabled_stick = function (enabled/* , color */) {
    this.toolEntity.find(e => e.name == 'stick').entity.enabled = enabled;
};

CameraPositionSetting.prototype.set_point_size = function (width, height) {
    if (width == undefined || height == undefined) return "value error";
    this.point_image.element.width = width;
    this.point_image.element.height = height;
    this.point_image.findByName('point_cross_width').element.width = width / 2;
    this.point_image.findByName('point_cross_width').element.height = height / 20;
    this.point_image.findByName('point_cross_height').element.width = width / 20;
    this.point_image.findByName('point_cross_height').element.height = height / 2;
};

CameraPositionSetting.prototype.add_point_position = function (x, y, z, reset) {
    if (reset) this.point.setPosition(this.point_defult_pos);
    if (x === "reset") {
        this.point.setLocalPosition(0, 0, 0);
        return "";
    }
    // console.log(x, y, z);
    if (x == undefined || y == undefined || z == undefined) return "value error";
    let pos = this.point.getPosition();
    pos.x += x / 10;
    pos.y += y / 10;
    pos.z += z / 10;
    this.point.setPosition(pos);
};

CameraPositionSetting.prototype.character_color_setting = function (value, color) {
    // this.character_model.render.material = this.character_colors[0].resources[0];

    if (value == undefined) return;
    let colors;
    if (value) colors = this.character_colors;
    else colors = this.character_colors_defult;

    if (typeof value == 'string') {
        color = value;
        value = true;
    }

    if (color && value) {
        this.character_colors[0].resources[0]._diffuse = new pc.Color().fromString(color);
        this.character_colors[0].resources[0].update();
    }

    var allMeshInstances = [];
    let renders = this.character_model.findComponents('render');
    for (i = 0; i < renders.length; ++i) {
        var meshInstances = renders[i].meshInstances;
        for (var j = 0; j < meshInstances.length; j++) {
            allMeshInstances.push(meshInstances[j]);
        }
    }

    for (i = 0; i < allMeshInstances.length; ++i) {
        var mesh = allMeshInstances[i];
        mesh.material = value ? colors[0].resources[0] : colors[i].resources[0];
    }
}

CameraPositionSetting.prototype.createUI = function () {
    this.ui_body = document.createElement('div');
    this.ui_body.classList.add('playcanvas_ui');
    document.querySelector('.view_box').appendChild(this.ui_body);
    // document.body.appendChild(this.ui_body);
    
    this.ui_body.innerHTML += `
    <button class="cam_init_btn" title="확대/축소 초기화"><i class="fa-solid fa-video"></i></button>`;
    this.ui_body.innerHTML += '<p class="help_text">컨트롤+마우스 휠을 사용하여 확대/축소를 할 수 있습니다.</p>';
    // document.
    let camDistanceResetFunc = function () { this.camDistanceReset(); }.bind(this);
    this.ui_body.querySelector('button').addEventListener('click', camDistanceResetFunc);
    // this.distanceResetBtn = document.createElement('button');
    // this.distanceResetBtn.style.position = ""
};

// swap method called for script hot-reloading
// inherit your script state here
// CameraPositionSetting.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/

// iconSpin.js
var IconSpin = pc.createScript('iconSpin');

// initialize code called once per entity
IconSpin.prototype.initialize = function () {
    this.yaw = 0;
    this.targetYaw = 0;
    this.pitch = 0;
    this.targetPitch = 0;
};

IconSpin.prototype.setIconRot = function (yaw, pitch) {
    this.targetYaw = yaw;
    this.targetPitch = pitch;
}

// update code called every frame
IconSpin.prototype.update = function (dt) {
    this.yaw = pc.math.lerp(this.yaw, this.targetYaw, 0.1);
    this.pitch = pc.math.lerp(this.pitch, this.targetPitch, 0.1);
    this.entity.setLocalEulerAngles(this.pitch, this.yaw, 0);
};

// swap method called for script hot-reloading
// inherit your script state here
// IconSpin.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/

// aniManager.js
var AniManager = pc.createScript('aniManager');

// initialize code called once per entity
AniManager.prototype.initialize = function () {
    this.ani = this.entity.anim;
};

// update code called every frame
AniManager.prototype.update = function (dt) {
    if (this.app.keyboard.wasPressed(pc.KEY_1)) {
        this.ani.setBoolean('pose1', false);
        this.ani.setBoolean('pose2', false);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_2)) {
        this.ani.setBoolean('pose1', true);
        this.ani.setBoolean('pose2', false);
    }
    if (this.app.keyboard.wasPressed(pc.KEY_3)) {
        this.ani.setBoolean('pose1', false);
        this.ani.setBoolean('pose2', true);
    }
};

// swap method called for script hot-reloading
// inherit your script state here
// AniManager.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/

