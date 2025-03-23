var StencilMask = pc.createScript("stencilMask");
StencilMask.attributes.add("maskID", {
    title: "Mask ID",
    description: "This mask id should be an integer between 0 and 7, as it relates as the power of 2 to the actual mask id.",
    type: "number",
    default: 0,
    min: 0,
    max: 7,
    precision: 0
}), StencilMask.prototype.initialize = function() {
    this._setStencil(), this.on("attr", this._attributeReloading)
}, StencilMask.prototype._setStencil = function() {
    var t = Math.pow(2, this.maskID),
        e = new pc.StencilParameters({
            readMask: t,
            writeMask: t,
            ref: t,
            zpass: pc.STENCILOP_REPLACE
        });
    this._setModelAsStencil(e)
}, StencilMask.prototype._setModelAsStencil = function(t) {
    var e = null;
    if (this.entity.render && (e = this.entity.render.meshInstances), this.entity.model && (e = this.entity.model.meshInstances), e && e.length > 0) {
        var i = e[0].material.clone();
        i.stencilBack = i.stencilFront = t, i.redWrite = i.greenWrite = i.blueWrite = i.alphaWrite = !1, e[0].material = i, i.update()
    }
}, StencilMask.prototype.swap = function(t) {
    this.on("attr", this._attributeReloading)
}, StencilMask.prototype._attributeReloading = function(t, e, i) {
    this._setStencil()
};
var StencilSubject = pc.createScript("stencilSubject");
StencilSubject.attributes.add("showInside", {
    title: "Show",
    description: "Determines whether this entity will be shown inside or outside of a mask with the given ID.\nIf multiple ID's are passed: \n- If 'Inside' is chosen, only the parts which are inside all of the given IDs will be shown.\n- If 'Outside' is chosen, the parts which are outside any of the given IDs will be shown.\n",
    type: "boolean",
    default: !0,
    enum: [{
        Inside: !0
    }, {
        Outside: !1
    }]
}), StencilSubject.attributes.add("maskIDs", {
    title: "Mask IDs",
    description: "These ID's have to be between 0 and 7.",
    type: "number",
    array: !0,
    default: [0]
}), StencilSubject.prototype.postInitialize = function() {
    var e = 0,
        t = 0;
    this._meshInstances = [];
    var s = this.entity.findComponents("render");
    for (e = 0; e < s.length; ++e)
        for (meshInstances = s[e].meshInstances, t = 0; t < meshInstances.length; t++) this._meshInstances.push(meshInstances[t]);
    var n = this.entity.findComponents("model");
    for (e = 0; e < n.length; ++e)
        for (meshInstances = n[e].meshInstances, t = 0; t < meshInstances.length; t++) this._meshInstances.push(meshInstances[t]);
    this._setStencil(), this.on("attr", this._attributeReloading)
}, StencilSubject.prototype._setStencil = function() {
    if (this._addMaskIDs()) {
        var e = new pc.StencilParameters({
            readMask: this.maskID,
            writeMask: this.maskID,
            ref: this.maskID,
            func: this.showInside ? pc.FUNC_EQUAL : pc.FUNC_NOTEQUAL
        });
        this._setStencilForModel(e), this._setStencilForParticle(e), this._setStencilForSpine(e), this._setStencilForSprite(e)
    }
}, StencilSubject.prototype._addMaskIDs = function() {
    if (this.maskID = 0, this.maskIDs.length > 8) return !1;
    for (var e = 0; e < this.maskIDs.length; ++e) {
        var t = Math.floor(this.maskIDs[e]);
        if (!(t >= 0 && t < 8)) return !1;
        this.maskID += Math.pow(2, t)
    }
    return !0
}, StencilSubject.prototype._setStencilForModel = function(e) {
    for (var t = 0; t < this._meshInstances.length; t++) {
        this._meshInstances[t].layer = this.app.scene.layers.getLayerByName("Before World");
        var s = this._meshInstances[t].material.clone();
        s.stencilBack = s.stencilFront = e, this._meshInstances[t].material = s
    }
}, StencilSubject.prototype._setStencilForParticle = function(e) {
    if (this.entity.particlesystem) {
        this.entity.particlesystem.emitter.meshInstance.layer = this.app.scene.layers.getLayerByName("Before World");
        var t = this.entity.particlesystem.emitter.material;
        t.stencilBack = t.stencilFront = e
    }
}, StencilSubject.prototype._setStencilForSpine = function(e) {
    if (this.entity.spine) {
        this.entity.spine.spine._model;
        for (var t = 0; t < this._meshInstances.length; t++) {
            this._meshInstances[t].layer = this.app.scene.layers.getLayerByName("Before World");
            var s = this._meshInstances[t].material;
            s.stencilBack = s.stencilFront = e
        }
    }
}, StencilSubject.prototype._setStencilForSprite = function(e) {
    if (this.entity.sprite) {
        var t = this.entity.sprite._model;
        t.meshInstances[0].layer = this.app.scene.layers.getLayerByName("Before World");
        var s = t.meshInstances[0].material.clone();
        s.stencilBack = s.stencilFront = e, t.meshInstances[0].material = s
    }
}, StencilSubject.prototype.swap = function(e) {
    this.on("attr", this._attributeReloading)
}, StencilSubject.prototype._attributeReloading = function(e, t, s) {
    this._setStencil()
};
var OrbitCamera = pc.createScript("orbitCamera");
OrbitCamera.attributes.add("distanceMax", {
    type: "number",
    default: 0,
    title: "Distance Max",
    description: "Setting this at 0 will give an infinite distance limit"
}), OrbitCamera.attributes.add("distanceMin", {
    type: "number",
    default: 0,
    title: "Distance Min"
}), OrbitCamera.attributes.add("pitchAngleMax", {
    type: "number",
    default: 90,
    title: "Pitch Angle Max (degrees)"
}), OrbitCamera.attributes.add("pitchAngleMin", {
    type: "number",
    default: -90,
    title: "Pitch Angle Min (degrees)"
}), OrbitCamera.attributes.add("yawAngleMax", {
    type: "number",
    default: 90,
    title: "yaw Angle Max (degrees)"
}), OrbitCamera.attributes.add("yawAngleMin", {
    type: "number",
    default: -90,
    title: "yaw Angle Min (degrees)"
}), OrbitCamera.attributes.add("startPosition", {
    type: "entity"
}), OrbitCamera.attributes.add("inertiaFactor", {
    type: "number",
    default: 0,
    title: "Inertia Factor",
    description: "Higher value means that the camera will continue moving after the user has stopped dragging. 0 is fully responsive."
}), OrbitCamera.attributes.add("focusEntity", {
    type: "entity",
    title: "Focus Entity",
    description: "Entity for the camera to focus on. If blank, then the camera will use the whole scene"
}), OrbitCamera.attributes.add("frameOnStart", {
    type: "boolean",
    default: !0,
    title: "Frame on Start",
    description: 'Frames the entity or scene at the start of the application."'
}), Object.defineProperty(OrbitCamera.prototype, "distance", {
    get: function() {
        return this._targetDistance
    },
    set: function(t) {
        this._targetDistance = this._clampDistance(t)
    }
}), Object.defineProperty(OrbitCamera.prototype, "pitch", {
    get: function() {
        return this._targetPitch
    },
    set: function(t) {
        this._targetPitch = this._clampPitchAngle(t)
    }
}), Object.defineProperty(OrbitCamera.prototype, "yaw", {
    get: function() {
        return this._targetYaw
    },
    set: function(t) {
        this._targetYaw = this._clampPitchYaw(t);
        var i = (this._targetYaw - this._yaw) % 360;
        this._targetYaw = i > 180 ? this._yaw - (360 - i) : i < -180 ? this._yaw + (360 + i) : this._yaw + i
    }
}), Object.defineProperty(OrbitCamera.prototype, "pivotPoint", {
    get: function() {
        return this._pivotPoint
    },
    set: function(t) {
        this._pivotPoint.copy(t)
    }
}), OrbitCamera.prototype.focus = function(t) {
    this._buildAabb(t, 0);
    var i = this._modelsAabb.halfExtents,
        e = Math.max(i.x, Math.max(i.y, i.z));
    e /= Math.tan(.5 * this.entity.camera.fov * pc.math.DEG_TO_RAD), e *= 2, this.distance = e, this._removeInertia(), this._pivotPoint.copy(this._modelsAabb.center)
}, OrbitCamera.distanceBetween = new pc.Vec3, OrbitCamera.prototype.resetAndLookAtPoint = function(t, i) {
    this.pivotPoint.copy(i), this.entity.setPosition(t), this.entity.lookAt(i);
    var e = OrbitCamera.distanceBetween;
    e.sub2(i, t), this.distance = e.length(), this.pivotPoint.copy(i);
    var a = this.entity.getRotation();
    this.yaw = this._calcYaw(a), this.pitch = this._calcPitch(a, this.yaw), this._removeInertia(), this._updatePosition()
}, OrbitCamera.prototype.resetAndLookAtEntity = function(t, i) {
    this._buildAabb(i, 0), this.resetAndLookAtPoint(t, this._modelsAabb.center)
}, OrbitCamera.prototype.reset = function(t, i, e) {
    this.pitch = i, this.yaw = t, this.distance = e, this._removeInertia()
}, OrbitCamera.prototype.initialize = function() {
    var t = this,
        onWindowResize = function() {
            t._checkAspectRatio()
        };
    window.addEventListener("resize", onWindowResize, !1), this._checkAspectRatio(), this._modelsAabb = new pc.BoundingBox, this._buildAabb(this.focusEntity || this.app.root, 0), this.entity.lookAt(this._modelsAabb.center), this._pivotPoint = new pc.Vec3, this._pivotPoint.copy(this._modelsAabb.center);
    var i = this.entity.getRotation();
    if (this._yaw = this._calcYaw(i), this._pitch = this._clampPitchAngle(this._calcPitch(i, this._yaw)), this.entity.setLocalEulerAngles(this._pitch, this._yaw, 0), this._distance = 0, this._targetYaw = this._yaw, this._targetPitch = this._pitch, this.frameOnStart) this.focus(this.focusEntity || this.app.root);
    else {
        var e = new pc.Vec3;
        e.sub2(this.entity.getPosition(), this._pivotPoint), this._distance = this._clampDistance(e.length())
    }
    this._targetDistance = this._distance, this.on("attr:distanceMin", (function(t, i) {
        this._targetDistance = this._clampDistance(this._distance)
    })), this.on("attr:distanceMax", (function(t, i) {
        this._targetDistance = this._clampDistance(this._distance)
    })), this.on("attr:pitchAngleMin", (function(t, i) {
        this._targetPitch = this._clampPitchAngle(this._pitch)
    })), this.on("attr:pitchAngleMax", (function(t, i) {
        this._targetPitch = this._clampPitchAngle(this._pitch)
    })), this.on("attr:focusEntity", (function(t, i) {
        this.frameOnStart ? this.focus(t || this.app.root) : this.resetAndLookAtEntity(this.entity.getPosition(), t || this.app.root)
    })), this.on("attr:frameOnStart", (function(t, i) {
        t && this.focus(this.focusEntity || this.app.root)
    })), this.on("destroy", (function() {
        window.removeEventListener("resize", onWindowResize, !1)
    })), this.pivotPoint.add(this.startPosition.getPosition())
}, OrbitCamera.prototype.setAngle = function(t) {
    this._pitch = t.x, this._yaw = t.y
}, OrbitCamera.prototype.update = function(t) {
    var i = 0 === this.inertiaFactor ? 1 : Math.min(t / this.inertiaFactor, 1);
    this._distance = pc.math.lerp(this._distance, this._targetDistance, i), this._yaw = pc.math.lerp(this._yaw, this._targetYaw, i), this._pitch = pc.math.lerp(this._pitch, this._targetPitch, i), this._updatePosition()
}, OrbitCamera.prototype._updatePosition = function() {
    this.entity.setLocalPosition(0, 0, 0), this.entity.setLocalEulerAngles(this._pitch, this._yaw, 0);
    var t = this.entity.getPosition();
    t.copy(this.entity.forward), t.scale(-this._distance), t.add(this.pivotPoint), this.entity.setPosition(t)
}, OrbitCamera.prototype._removeInertia = function() {
    this._yaw = this._targetYaw, this._pitch = this._targetPitch, this._distance = this._targetDistance
}, OrbitCamera.prototype._checkAspectRatio = function() {
    var t = this.app.graphicsDevice.height,
        i = this.app.graphicsDevice.width;
    this.entity.camera.horizontalFov = t > i
}, OrbitCamera.prototype._buildAabb = function(t, i) {
    var e, a = 0,
        n = 0;
    if (t instanceof pc.Entity) {
        var s = [],
            r = t.findComponents("render");
        for (a = 0; a < r.length; ++a)
            for (e = r[a].meshInstances, n = 0; n < e.length; n++) s.push(e[n]);
        var h = t.findComponents("model");
        for (a = 0; a < h.length; ++a)
            for (e = h[a].meshInstances, n = 0; n < e.length; n++) s.push(e[n]);
        for (a = 0; a < s.length; a++) 0 === i ? this._modelsAabb.copy(s[a].aabb) : this._modelsAabb.add(s[a].aabb), i += 1
    }
    for (a = 0; a < t.children.length; ++a) i += this._buildAabb(t.children[a], i);
    return i
}, OrbitCamera.prototype._calcYaw = function(t) {
    var i = new pc.Vec3;
    return t.transformVector(pc.Vec3.FORWARD, i), Math.atan2(-i.x, -i.z) * pc.math.RAD_TO_DEG
}, OrbitCamera.prototype._clampDistance = function(t) {
    return this.distanceMax > 0 ? pc.math.clamp(t, this.distanceMin, this.distanceMax) : Math.max(t, this.distanceMin)
}, OrbitCamera.prototype._clampPitchAngle = function(t) {
    return pc.math.clamp(t, -this.pitchAngleMax, -this.pitchAngleMin)
}, OrbitCamera.prototype._clampPitchYaw = function(t) {
    return pc.math.clamp(t, -this.yawAngleMax, -this.yawAngleMin)
}, OrbitCamera.quatWithoutYaw = new pc.Quat, OrbitCamera.yawOffset = new pc.Quat, OrbitCamera.prototype._calcPitch = function(t, i) {
    var e = OrbitCamera.quatWithoutYaw,
        a = OrbitCamera.yawOffset;
    a.setFromEulerAngles(0, -i, 0), e.mul2(a, t);
    var n = new pc.Vec3;
    return e.transformVector(pc.Vec3.FORWARD, n), Math.atan2(n.y, -n.z) * pc.math.RAD_TO_DEG
};
var KeyboardInput = pc.createScript("keyboardInput");
KeyboardInput.prototype.initialize = function() {
    this.orbitCamera = this.entity.script.orbitCamera
}, KeyboardInput.prototype.postInitialize = function() {
    this.orbitCamera && (this.startDistance = this.orbitCamera.distance, this.startYaw = this.orbitCamera.yaw, this.startPitch = this.orbitCamera.pitch, this.startPivotPosition = this.orbitCamera.pivotPoint.clone())
}, KeyboardInput.prototype.update = function(t) {
    this.orbitCamera && this.app.keyboard.wasPressed(pc.KEY_SPACE) && (this.orbitCamera.reset(this.startYaw, this.startPitch, this.startDistance), this.orbitCamera.pivotPoint = this.startPivotPosition)
};
var TouchInput = pc.createScript("touchInput");
TouchInput.attributes.add("orbitSensitivity", {
    type: "number",
    default: .4,
    title: "Orbit Sensitivity",
    description: "How fast the camera moves around the orbit. Higher is faster"
}), TouchInput.attributes.add("distanceSensitivity", {
    type: "number",
    default: .2,
    title: "Distance Sensitivity",
    description: "How fast the camera moves in and out. Higher is faster"
}), TouchInput.prototype.initialize = function() {
    this.orbitCamera = this.entity.script.orbitCamera, this.lastTouchPoint = new pc.Vec2, this.lastPinchMidPoint = new pc.Vec2, this.lastPinchDistance = 0, this.orbitCamera && this.app.touch && (this.app.touch.on(pc.EVENT_TOUCHSTART, this.onTouchStartEndCancel, this), this.app.touch.on(pc.EVENT_TOUCHEND, this.onTouchStartEndCancel, this), this.app.touch.on(pc.EVENT_TOUCHCANCEL, this.onTouchStartEndCancel, this), this.app.touch.on(pc.EVENT_TOUCHMOVE, this.onTouchMove, this), this.on("destroy", (function() {
        this.app.touch.off(pc.EVENT_TOUCHSTART, this.onTouchStartEndCancel, this), this.app.touch.off(pc.EVENT_TOUCHEND, this.onTouchStartEndCancel, this), this.app.touch.off(pc.EVENT_TOUCHCANCEL, this.onTouchStartEndCancel, this), this.app.touch.off(pc.EVENT_TOUCHMOVE, this.onTouchMove, this)
    })))
}, TouchInput.prototype.getPinchDistance = function(t, i) {
    var o = t.x - i.x,
        n = t.y - i.y;
    return Math.sqrt(o * o + n * n)
}, TouchInput.prototype.calcMidPoint = function(t, i, o) {
    o.set(i.x - t.x, i.y - t.y), o.scale(.5), o.x += t.x, o.y += t.y
}, TouchInput.prototype.onTouchStartEndCancel = function(t) {
    var i = t.touches;
    1 == i.length ? this.lastTouchPoint.set(i[0].x, i[0].y) : 2 == i.length && (this.lastPinchDistance = this.getPinchDistance(i[0], i[1]), this.calcMidPoint(i[0], i[1], this.lastPinchMidPoint))
}, TouchInput.fromWorldPoint = new pc.Vec3, TouchInput.toWorldPoint = new pc.Vec3, TouchInput.worldDiff = new pc.Vec3, TouchInput.prototype.pan = function(t) {
    var i = TouchInput.fromWorldPoint,
        o = TouchInput.toWorldPoint,
        n = TouchInput.worldDiff,
        h = this.entity.camera,
        c = this.orbitCamera.distance;
    h.screenToWorld(t.x, t.y, c, i), h.screenToWorld(this.lastPinchMidPoint.x, this.lastPinchMidPoint.y, c, o), n.sub2(o, i), this.orbitCamera.pivotPoint.add(n)
}, TouchInput.pinchMidPoint = new pc.Vec2, TouchInput.prototype.onTouchMove = function(t) {
    var i = TouchInput.pinchMidPoint,
        o = t.touches;
    if (1 == o.length) {
        var n = o[0];
        this.orbitCamera.pitch -= (n.y - this.lastTouchPoint.y) * this.orbitSensitivity, this.orbitCamera.yaw -= (n.x - this.lastTouchPoint.x) * this.orbitSensitivity, this.lastTouchPoint.set(n.x, n.y)
    } else if (2 == o.length) {
        var h = this.getPinchDistance(o[0], o[1]),
            c = h - this.lastPinchDistance;
        this.lastPinchDistance = h, this.orbitCamera.distance -= c * this.distanceSensitivity * .1 * (.1 * this.orbitCamera.distance), this.calcMidPoint(o[0], o[1], i), this.pan(i), this.lastPinchMidPoint.copy(i)
    }
};
var MouseInput = pc.createScript("mouseInput");
MouseInput.attributes.add("orbitSensitivity", {
    type: "number",
    default: .3,
    title: "Orbit Sensitivity",
    description: "How fast the camera moves around the orbit. Higher is faster"
}), MouseInput.attributes.add("distanceSensitivity", {
    type: "number",
    default: .15,
    title: "Distance Sensitivity",
    description: "How fast the camera moves in and out. Higher is faster"
}), MouseInput.attributes.add("rightMouse", {
    type: "boolean",
    title: "우클릭 화면 이동"
}), MouseInput.prototype.initialize = function() {
    if (this.rotCheck = this.app.root.findByName("test Screen").script.testButton, this.orbitCamera = this.entity.script.orbitCamera, this.orbitCamera) {
        var t = this,
            onMouseOut = function(o) {
                t.onMouseOut(o)
            };
        this.app.mouse.on(pc.EVENT_MOUSEDOWN, this.onMouseDown, this), this.app.mouse.on(pc.EVENT_MOUSEUP, this.onMouseUp, this), this.app.mouse.on(pc.EVENT_MOUSEMOVE, this.onMouseMove, this), this.app.mouse.on(pc.EVENT_MOUSEWHEEL, this.onMouseWheel, this), window.addEventListener("mouseout", onMouseOut, !1), this.on("destroy", (function() {
            this.app.mouse.off(pc.EVENT_MOUSEDOWN, this.onMouseDown, this), this.app.mouse.off(pc.EVENT_MOUSEUP, this.onMouseUp, this), this.app.mouse.off(pc.EVENT_MOUSEMOVE, this.onMouseMove, this), this.app.mouse.off(pc.EVENT_MOUSEWHEEL, this.onMouseWheel, this), window.removeEventListener("mouseout", onMouseOut, !1)
        }))
    }
    this.app.mouse.disableContextMenu(), this.lookButtonDown = !1, this.panButtonDown = !1, this.lastPoint = new pc.Vec2
}, MouseInput.fromWorldPoint = new pc.Vec3, MouseInput.toWorldPoint = new pc.Vec3, MouseInput.worldDiff = new pc.Vec3, MouseInput.prototype.pan = function(t) {
    var o = MouseInput.fromWorldPoint,
        i = MouseInput.toWorldPoint,
        e = MouseInput.worldDiff,
        s = this.entity.camera,
        n = this.orbitCamera.distance;
    s.screenToWorld(t.x, t.y, n, o), s.screenToWorld(this.lastPoint.x, this.lastPoint.y, n, i), e.sub2(i, o), this.orbitCamera.pivotPoint.x + e.x < 4 && this.orbitCamera.pivotPoint.x + e.x > 0 && this.orbitCamera.pivotPoint.y + e.y < 2 && this.orbitCamera.pivotPoint.y + e.y > -2 && this.orbitCamera.pivotPoint.z + e.z < 2 && this.orbitCamera.pivotPoint.z + e.z > -2 && this.orbitCamera.pivotPoint.add(e)
}, MouseInput.prototype.onMouseDown = function(t) {
    switch (t.button) {
        case pc.MOUSEBUTTON_LEFT:
            this.lookButtonDown = !0;
            break;
        case pc.MOUSEBUTTON_MIDDLE:
        case pc.MOUSEBUTTON_RIGHT:
            this.panButtonDown = !0
    }
}, MouseInput.prototype.onMouseUp = function(t) {
    switch (t.button) {
        case pc.MOUSEBUTTON_LEFT:
            this.lookButtonDown = !1;
            break;
        case pc.MOUSEBUTTON_MIDDLE:
        case pc.MOUSEBUTTON_RIGHT:
            this.panButtonDown = !1
    }
}, MouseInput.prototype.onMouseMove = function(t) {
    pc.app.mouse;
    this.rotCheck.isRotation || (this.lookButtonDown ? (this.orbitCamera.pitch -= t.dy * this.orbitSensitivity, this.orbitCamera.yaw -= t.dx * this.orbitSensitivity) : this.panButtonDown && this.rightMouse && this.pan(t), this.lastPoint.set(t.x, t.y))
}, MouseInput.prototype.onMouseWheel = function(t) {
    this.orbitCamera.distance -= t.wheel * this.distanceSensitivity * (.1 * this.orbitCamera.distance), t.event.preventDefault()
}, MouseInput.prototype.onMouseOut = function(t) {
    this.lookButtonDown = !1, this.panButtonDown = !1
};
var Occulder = pc.createScript("occluder");
Occulder.attributes.add("layerName", {
    type: "string"
}), Occulder.attributes.add("materialAsset", {
    type: "asset",
    assetType: "material"
}), Occulder.prototype.initialize = function() {
    const e = this.materialAsset.resource;
    e.redWrite = !1, e.greenWrite = !1, e.blueWrite = !1, e.alphaWrite = !1, e.update();
    const t = this.app.scene.layers.getLayerByName(this.layerName),
        r = this.entity.findComponents("render");
    for (let a = 0; a < r.length; ++a) {
        const s = r[a].meshInstances;
        for (let t = 0; t < s.length; ++t) s[t].material = e;
        r[a].layers = [t.id]
    }
}, Occulder.prototype.update = function(e) {};

function SSAOEffect(e, t) {
    pc.PostEffect.call(this, e), this.ssaoScript = t, this.needsDepthBuffer = !0;
    var i = [e.webgl2 ? "#version 300 es\n\n" + pc.shaderChunks.gles3VS : "", e.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n", "attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "   vUv0 = (aPosition.xy + 1.0) * 0.5;", "   gl_Position = vec4(aPosition, 0.0, 1.0);", "}"].join("\n");
    this.ssaoShader = new pc.Shader(e, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: i,
        fshader: [e.webgl2 ? "#version 300 es\n\n" + pc.shaderChunks.gles3PS : "", e.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n", "precision " + e.precision + " float;", pc.shaderChunks.screenDepthPS, "", "varying vec2 vUv0;", "", "uniform sampler2D uColorBuffer;", "uniform vec4 uResolution;", "", "uniform float uAspect;", "", "#define saturate(x) clamp(x,0.0,1.0)", "", "// Largely based on 'Dominant Light Shadowing'", "// 'Lighting Technology of The Last of Us Part II' by Hawar Doghramachi, Naughty Dog, LLC", "", "const float kSSCTLog2LodRate = 3.0;", "", "highp float getWFromProjectionMatrix(const mat4 p, const vec3 v) {", "    // this essentially returns (p * vec4(v, 1.0)).w, but we make some assumptions", "    // this assumes a perspective projection", "    return -v.z;", "    // this assumes a perspective or ortho projection", "    // return p[2][3] * v.z + p[3][3];", "}", "", "highp float getViewSpaceZFromW(const mat4 p, const float w) {", "    // this assumes a perspective projection", "    return -w;", "    // this assumes a perspective or ortho projection", "   // return (w - p[3][3]) / p[2][3];", "}", "", "", "const float kLog2LodRate = 3.0;", "", "vec2 sq(const vec2 a) {", "    return a * a;", "}", "", "uniform float uInvFarPlane;", "", "vec2 pack(highp float depth) {", "// we need 16-bits of precision", "    highp float z = clamp(depth * uInvFarPlane, 0.0, 1.0);", "    highp float t = floor(256.0 * z);", "    mediump float hi = t * (1.0 / 256.0);   // we only need 8-bits of precision", "    mediump float lo = (256.0 * z) - t;     // we only need 8-bits of precision", "    return vec2(hi, lo);", "}", "", "// random number between 0 and 1, using interleaved gradient noise", "float random(const highp vec2 w) {", "    const vec3 m = vec3(0.06711056, 0.00583715, 52.9829189);", "    return fract(m.z * fract(dot(w, m.xy)));", "}", "", "// returns the frag coord in the GL convention with (0, 0) at the bottom-left", "highp vec2 getFragCoord() {", "    return gl_FragCoord.xy;", "}", "", "highp vec3 computeViewSpacePositionFromDepth(highp vec2 uv, highp float linearDepth) {", "    return vec3((0.5 - uv) * vec2(uAspect, 1.0) * linearDepth, linearDepth);", "}", "", "highp vec3 faceNormal(highp vec3 dpdx, highp vec3 dpdy) {", "    return normalize(cross(dpdx, dpdy));", "}", "", "// Compute normals using derivatives, which essentially results in half-resolution normals", "// this creates arifacts around geometry edges.", "// Note: when using the spirv optimizer, this results in much slower execution time because", "//       this whole expression is inlined in the AO loop below.", "highp vec3 computeViewSpaceNormal(const highp vec3 position) {", "    return faceNormal(dFdx(position), dFdy(position));", "}", "", "// Compute normals directly from the depth texture, resulting in full resolution normals", "// Note: This is actually as cheap as using derivatives because the texture fetches", "//       are essentially equivalent to textureGather (which we don't have on ES3.0),", "//       and this is executed just once.", "highp vec3 computeViewSpaceNormal(const highp vec3 position, const highp vec2 uv) {", "    highp vec2 uvdx = uv + vec2(uResolution.z, 0.0);", "    highp vec2 uvdy = uv + vec2(0.0, uResolution.w);", "    highp vec3 px = computeViewSpacePositionFromDepth(uvdx, -getLinearScreenDepth(uvdx));", "    highp vec3 py = computeViewSpacePositionFromDepth(uvdy, -getLinearScreenDepth(uvdy));", "    highp vec3 dpdx = px - position;", "    highp vec3 dpdy = py - position;", "    return faceNormal(dpdx, dpdy);", "}", "", "// Ambient Occlusion, largely inspired from:", "// 'The Alchemy Screen-Space Ambient Obscurance Algorithm' by Morgan McGuire", "// 'Scalable Ambient Obscurance' by Morgan McGuire, Michael Mara and David Luebke", "", "uniform vec2 uSampleCount;", "uniform float uSpiralTurns;", "", "#define PI (3.14159)", "", "vec3 tapLocation(float i, const float noise) {", "    float offset = ((2.0 * PI) * 2.4) * noise;", "    float angle = ((i * uSampleCount.y) * uSpiralTurns) * (2.0 * PI) + offset;", "    float radius = (i + noise + 0.5) * uSampleCount.y;", "    return vec3(cos(angle), sin(angle), radius * radius);", "}", "", "highp vec2 startPosition(const float noise) {", "    float angle = ((2.0 * PI) * 2.4) * noise;", "    return vec2(cos(angle), sin(angle));", "}", "", "uniform vec2 uAngleIncCosSin;", "", "highp mat2 tapAngleStep() {", "    highp vec2 t = uAngleIncCosSin;", "    return mat2(t.x, t.y, -t.y, t.x);", "}", "", "vec3 tapLocationFast(float i, vec2 p, const float noise) {", "    float radius = (i + noise + 0.5) * uSampleCount.y;", "    return vec3(p, radius * radius);", "}", "", "uniform float uMaxLevel;", "uniform float uInvRadiusSquared;", "uniform float uMinHorizonAngleSineSquared;", "uniform float uBias;", "uniform float uPeak2;", "", "void computeAmbientOcclusionSAO(inout float occlusion, float i, float ssDiskRadius,", "        const highp vec2 uv,  const highp vec3 origin, const vec3 normal,", "        const vec2 tapPosition, const float noise) {", "", "    vec3 tap = tapLocationFast(i, tapPosition, noise);", "", "    float ssRadius = max(1.0, tap.z * ssDiskRadius);", "", "    vec2 uvSamplePos = uv + vec2(ssRadius * tap.xy) * uResolution.zw;", "", "    float level = clamp(floor(log2(ssRadius)) - kLog2LodRate, 0.0, float(uMaxLevel));", "    highp float occlusionDepth = -getLinearScreenDepth(uvSamplePos);", "    highp vec3 p = computeViewSpacePositionFromDepth(uvSamplePos, occlusionDepth);", "", "    // now we have the sample, compute AO", "    vec3 v = p - origin;        // sample vector", "    float vv = dot(v, v);       // squared distance", "    float vn = dot(v, normal);  // distance * cos(v, normal)", "", "    // discard samples that are outside of the radius, preventing distant geometry to", "    // cast shadows -- there are many functions that work and choosing one is an artistic", "    // decision.", "    float w = max(0.0, 1.0 - vv * uInvRadiusSquared);", "    w = w*w;", "", "    // discard samples that are too close to the horizon to reduce shadows cast by geometry", "    // not sufficiently tessellated. The goal is to discard samples that form an angle 'beta'", "    // smaller than 'epsilon' with the horizon. We already have dot(v,n) which is equal to the", "    // sin(beta) * |v|. So the test simplifies to vn^2 < vv * sin(epsilon)^2.", "    w *= step(vv * uMinHorizonAngleSineSquared, vn * vn);", "", "    occlusion += w * max(0.0, vn + origin.z * uBias) / (vv + uPeak2);", "}", "", "uniform float uProjectionScaleRadius;", "uniform float uIntensity;", "", "float scalableAmbientObscurance(highp vec2 uv, highp vec3 origin, vec3 normal) {", "    float noise = random(getFragCoord());", "    highp vec2 tapPosition = startPosition(noise);", "    highp mat2 angleStep = tapAngleStep();", "", "    // Choose the screen-space sample radius", "    // proportional to the projected area of the sphere", "    float ssDiskRadius = -(uProjectionScaleRadius / origin.z);", "", "    float occlusion = 0.0;", e.webgl2 ? "    for (float i = 0.0; i < uSampleCount.x; i += 1.0) {" : "   const float maxSampleCount = 256.0;   for (float i = 0.0; i < maxSampleCount; i += 1.0) {       if (i >= uSampleCount.x) break;", "        computeAmbientOcclusionSAO(occlusion, i, ssDiskRadius, uv, origin, normal, tapPosition, noise);", "        tapPosition = angleStep * tapPosition;", "    }", "    return sqrt(occlusion * uIntensity);", "}", "", "uniform float uPower;", "", "void main() {", "    highp vec2 uv = vUv0; //variable_vertex.xy; // interpolated to pixel center", "", "    highp float depth = -getLinearScreenDepth(vUv0);", "    highp vec3 origin = computeViewSpacePositionFromDepth(uv, depth);", "    vec3 normal = computeViewSpaceNormal(origin, uv);", "", "    float occlusion = 0.0;", "", "    if (uIntensity > 0.0) {", "        occlusion = scalableAmbientObscurance(uv, origin, normal);", "    }", "", "    // occlusion to visibility", "    float aoVisibility = pow(saturate(1.0 - occlusion), uPower);", "", "    vec4 inCol = vec4(1.0, 1.0, 1.0, 1.0); //texture2D( uColorBuffer,  uv );", "", "    gl_FragColor.r = aoVisibility; //postProcess.color.rgb = vec3(aoVisibility, pack(origin.z));", "}", "", "void main_old()", "{", "    vec2 aspectCorrect = vec2( 1.0, uAspect );", "", "    float depth = getLinearScreenDepth(vUv0);", "    gl_FragColor.r = fract(floor(depth*256.0*256.0)),fract(floor(depth*256.0)),fract(depth);", "}"].join("\n")
    }), this.blurShader = new pc.Shader(e, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: i,
        fshader: [e.webgl2 ? "#version 300 es\n\n" + pc.shaderChunks.gles3PS : "", e.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n", "precision " + e.precision + " float;", pc.shaderChunks.screenDepthPS, "", "varying vec2 vUv0;", "", "uniform sampler2D uColorBuffer;", "uniform sampler2D uSSAOBuffer;", "uniform vec4 uResolution;", "", "uniform float uAspect;", "", "uniform int uBilatSampleCount;", "uniform float uFarPlaneOverEdgeDistance;", "uniform float uBrightness;", "", "float random(const highp vec2 w) {", "    const vec3 m = vec3(0.06711056, 0.00583715, 52.9829189);", "    return fract(m.z * fract(dot(w, m.xy)));", "}", "", "float bilateralWeight(in float depth, in float sampleDepth) {", "    float diff = (sampleDepth - depth) * uFarPlaneOverEdgeDistance;", "    return max(0.0, 1.0 - diff * diff);", "}", "", "void tap(inout float sum, inout float totalWeight, float weight, float depth, vec2 position) {", "    // ambient occlusion sample", "    float ssao = texture2D( uSSAOBuffer, position ).r;", "    float tdepth = -getLinearScreenDepth( position );", "", "    // bilateral sample", "    float bilateral = bilateralWeight(depth, tdepth);", "    bilateral *= weight;", "    sum += ssao * bilateral;", "    totalWeight += bilateral;", "}", "", "void main() {", "    highp vec2 uv = vUv0; // variable_vertex.xy; // interpolated at pixel's center", "", "    // we handle the center pixel separately because it doesn't participate in bilateral filtering", "    float depth = -getLinearScreenDepth(vUv0); // unpack(data.gb);", "    float totalWeight = 0.0; // float(uBilatSampleCount*2+1)*float(uBilatSampleCount*2+1);", "    float ssao = texture2D( uSSAOBuffer, vUv0 ).r;", "    float sum = ssao * totalWeight;", "", e.webgl2 ? "    for (int x = -uBilatSampleCount; x <= uBilatSampleCount; x++) {       for (int y = -uBilatSampleCount; y < uBilatSampleCount; y++) {" : "    for (int x = -4; x <= 4; x++) {       for (int y = -4; y < 4; y++) {", "           float weight = 1.0;", "           vec2 offset = vec2(x,y)*uResolution.zw;", "           tap(sum, totalWeight, weight, depth, uv + offset);", "       }", "    }", "", "    float ao = sum / totalWeight;", "", "    // simple dithering helps a lot (assumes 8 bits target)", "    // this is most useful with high quality/large blurs", "    // ao += ((random(gl_FragCoord.xy) - 0.5) / 255.0);", "", "    ao = mix(ao, 1.0, uBrightness);", "    gl_FragColor.a = ao;", "}"].join("\n")
    }), this.outputShader = new pc.Shader(e, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: i,
        fshader: [e.webgl2 ? "#version 300 es\n\n" + pc.shaderChunks.gles3PS : "", e.webgl2 ? "" : "#extension GL_OES_standard_derivatives: enable\n", "precision " + e.precision + " float;", "varying vec2 vUv0;", "uniform float uUpscale;", "uniform sampler2D uColorBuffer;", "uniform sampler2D uSSAOBuffer;", "", "void main(void)", "{", "    vec4 inCol = texture2D( uColorBuffer,  vUv0 );", "    float ssao = texture2D( uSSAOBuffer,  vUv0 ).a;", "    gl_FragColor.rgb = inCol.rgb * ssao;", "    gl_FragColor.a = inCol.a;", "}"].join("\n")
    }), this.radius = 4, this.brightness = 0, this.samples = 20, this.downscale = 1, this.resize(null)
}
SSAOEffect.prototype = Object.create(pc.PostEffect.prototype), SSAOEffect.prototype.constructor = SSAOEffect, SSAOEffect.prototype.resize = function(e) {
    var t, i;
    if (null === e ? (t = Math.ceil(this.device.width / this.device.maxPixelRatio / this.downscale), i = Math.ceil(this.device.height / this.device.maxPixelRatio / this.downscale)) : (t = Math.ceil(e.colorBuffer.width / this.device.maxPixelRatio / this.downscale), i = Math.ceil(e.colorBuffer.height / this.device.maxPixelRatio / this.downscale)), t !== this.width || i !== this.height) {
        this.width = t, this.height = i, this.target && (this.target.destroyFrameBuffers(), this.target.destroyTextureBuffers(), this.target.destroy(), this.target = null, this.blurTarget.destroyFrameBuffers(), this.blurTarget.destroyTextureBuffers(), this.blurTarget.destroy(), this.blurTarget = null);
        var o = new pc.Texture(this.device, {
            format: pc.PIXELFORMAT_R8_G8_B8_A8,
            minFilter: pc.FILTER_LINEAR,
            magFilter: pc.FILTER_LINEAR,
            addressU: pc.ADDRESS_CLAMP_TO_EDGE,
            addressV: pc.ADDRESS_CLAMP_TO_EDGE,
            width: this.width,
            height: this.height,
            mipmaps: !1
        });
        o.name = "ssao", this.target = new pc.RenderTarget({
            colorBuffer: o,
            depth: !1
        });
        var a = new pc.Texture(this.device, {
            format: pc.PIXELFORMAT_R8_G8_B8_A8,
            minFilter: pc.FILTER_LINEAR,
            magFilter: pc.FILTER_LINEAR,
            addressU: pc.ADDRESS_CLAMP_TO_EDGE,
            addressV: pc.ADDRESS_CLAMP_TO_EDGE,
            width: this.width,
            height: this.height,
            mipmaps: !1
        });
        this.blurTarget = new pc.RenderTarget({
            colorBuffer: a,
            depth: !1
        })
    }
}, Object.assign(SSAOEffect.prototype, {
    render: function(e, t, i) {
        var o = this.device,
            a = o.scope,
            s = this.samples,
            r = 1 / (s - .5) * 10 * 2 * 3.141,
            n = this.radius,
            l = .1 * n,
            u = 2 * l * 3.141 * .125,
            c = .5 * o.height,
            h = this.ssaoScript.entity.camera.farClip;
        a.resolve("uAspect").setValue(this.width / this.height), a.resolve("uResolution").setValue([this.width, this.height, 1 / this.width, 1 / this.height]), a.resolve("uBrightness").setValue(this.brightness), a.resolve("uInvFarPlane").setValue(1 / h), a.resolve("uSampleCount").setValue([s, 1 / s]), a.resolve("uSpiralTurns").setValue(10), a.resolve("uAngleIncCosSin").setValue([Math.cos(r), Math.sin(r)]), a.resolve("uMaxLevel").setValue(0), a.resolve("uInvRadiusSquared").setValue(1 / (n * n)), a.resolve("uMinHorizonAngleSineSquared").setValue(0), a.resolve("uBias").setValue(.001), a.resolve("uPeak2").setValue(l * l), a.resolve("uIntensity").setValue(u), a.resolve("uPower").setValue(1), a.resolve("uProjectionScaleRadius").setValue(c * n), pc.drawFullscreenQuad(o, this.target, this.vertexBuffer, this.ssaoShader, i), a.resolve("uSSAOBuffer").setValue(this.target.colorBuffer), a.resolve("uFarPlaneOverEdgeDistance").setValue(1), a.resolve("uBilatSampleCount").setValue(4), pc.drawFullscreenQuad(o, this.blurTarget, this.vertexBuffer, this.blurShader, i), a.resolve("uUpscale").setValue(1 / this.downscale), a.resolve("uSSAOBuffer").setValue(this.blurTarget.colorBuffer), a.resolve("uColorBuffer").setValue(e.colorBuffer), pc.drawFullscreenQuad(o, t, this.vertexBuffer, this.outputShader, i)
    }
});
var SSAO = pc.createScript("ssao");
SSAO.attributes.add("radius", {
    type: "number",
    default: 4,
    min: 0,
    max: 20,
    title: "Radius"
}), SSAO.attributes.add("brightness", {
    type: "number",
    default: 0,
    min: 0,
    max: 1,
    title: "Brightness"
}), SSAO.attributes.add("samples", {
    type: "number",
    default: 16,
    min: 1,
    max: 256,
    title: "Samples"
}), SSAO.attributes.add("downscale", {
    type: "number",
    default: 1,
    min: 1,
    max: 4,
    title: "Downscale"
}), SSAO.prototype.initialize = function() {
    this.effect = new SSAOEffect(this.app.graphicsDevice, this), this.effect.radius = this.radius, this.effect.brightness = this.brightness, this.effect.samples = this.samples, this.effect.downscale = this.downscale, this.on("attr", (function(e, t) {
        this.effect[e] = t
    }), this);
    var e = this.entity.camera.postEffects;
    e.addEffect(this.effect), this.on("state", (function(t) {
        t ? e.addEffect(this.effect) : e.removeEffect(this.effect)
    })), this.on("attr:downscale", (() => {
        this.enabled && this.effect.resize()
    })), this.on("destroy", (function() {
        e.removeEffect(this.effect)
    }))
};

function HueSaturationEffect(t) {
    pc.PostEffect.call(this, t), this.shader = new pc.Shader(t, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: ["attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    gl_Position = vec4(aPosition, 0.0, 1.0);", "    vUv0 = (aPosition.xy + 1.0) * 0.5;", "}"].join("\n"),
        fshader: ["precision " + t.precision + " float;", "", "uniform sampler2D uColorBuffer;", "uniform float uHue;", "uniform float uSaturation;", "", "varying vec2 vUv0;", "", "void main() {", "    gl_FragColor = texture2D( uColorBuffer, vUv0 );", "", "    float angle = uHue * 3.14159265;", "    float s = sin(angle), c = cos(angle);", "    vec3 weights = (vec3(2.0 * c, -sqrt(3.0) * s - c, sqrt(3.0) * s - c) + 1.0) / 3.0;", "    float len = length(gl_FragColor.rgb);", "    gl_FragColor.rgb = vec3(", "        dot(gl_FragColor.rgb, weights.xyz),", "        dot(gl_FragColor.rgb, weights.zxy),", "        dot(gl_FragColor.rgb, weights.yzx)", "    );", "", "    float average = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3.0;", "    if (uSaturation > 0.0) {", "        gl_FragColor.rgb += (average - gl_FragColor.rgb) * (1.0 - 1.0 / (1.001 - uSaturation));", "    } else {", "        gl_FragColor.rgb += (average - gl_FragColor.rgb) * (-uSaturation);", "    }", "}"].join("\n")
    }), this.hue = 0, this.saturation = 0
}
HueSaturationEffect.prototype = Object.create(pc.PostEffect.prototype), HueSaturationEffect.prototype.constructor = HueSaturationEffect, Object.assign(HueSaturationEffect.prototype, {
    render: function(t, e, r) {
        var a = this.device,
            o = a.scope;
        o.resolve("uHue").setValue(this.hue), o.resolve("uSaturation").setValue(this.saturation), o.resolve("uColorBuffer").setValue(t.colorBuffer), pc.drawFullscreenQuad(a, e, this.vertexBuffer, this.shader, r)
    }
});
var HueSaturation = pc.createScript("hueSaturation");
HueSaturation.attributes.add("hue", {
    type: "number",
    default: 0,
    min: -1,
    max: 1,
    title: "Hue"
}), HueSaturation.attributes.add("saturation", {
    type: "number",
    default: 0,
    min: -1,
    max: 1,
    title: "Saturation"
}), HueSaturation.prototype.initialize = function() {
    this.effect = new HueSaturationEffect(this.app.graphicsDevice), this.effect.hue = this.hue, this.effect.saturation = this.saturation, this.on("attr", (function(t, e) {
        this.effect[t] = e
    }), this);
    var t = this.entity.camera.postEffects;
    t.addEffect(this.effect), this.on("state", (function(e) {
        e ? t.addEffect(this.effect) : t.removeEffect(this.effect)
    })), this.on("destroy", (function() {
        t.removeEffect(this.effect)
    }))
};
pc.extend(pc, function() {
    var PixelatePostEffect = function(e, i, t) {
        this.shader = new pc.Shader(e, {
            attributes: {
                aPosition: pc.SEMANTIC_POSITION
            },
            vshader: ["attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    gl_Position = vec4(aPosition, 0.0, 1.0);", "    vUv0 = (aPosition.xy + 1.0) * 0.5;", "}"].join("\n"),
            fshader: ["precision " + e.precision + " float;", "", "uniform vec2 uResolution;", "uniform float uPixelize;", "uniform sampler2D uColorBuffer;", "", "varying vec2 vUv0;", "", "void main() {", "    vec2 uv = gl_FragCoord.xy / uResolution.xy;", "    vec2 div = vec2(uResolution.x * uPixelize / uResolution.y, uPixelize);", "    uv = floor(uv * div)/div;", "    vec4 color = texture2D(uColorBuffer, uv);", "    gl_FragColor = color;", "}"].join("\n")
        }), this.resolution = new Float32Array(2), this.pixelize = 100
    };
    return (PixelatePostEffect = pc.inherits(PixelatePostEffect, pc.PostEffect)).prototype = pc.extend(PixelatePostEffect.prototype, {
        render: function(e, i, t) {
            var o = this.device,
                r = o.scope;
            r.resolve("uColorBuffer").setValue(e.colorBuffer), this.resolution[0] = o.width, this.resolution[1] = o.height, r.resolve("uResolution").setValue(this.resolution), r.resolve("uPixelize").setValue(this.pixelize), pc.drawFullscreenQuad(o, i, this.vertexBuffer, this.shader, t)
        }
    }), {
        PixelatePostEffect: PixelatePostEffect
    }
}());
var PostEffectPixelate = pc.createScript("PostEffectPixelate");
PostEffectPixelate.attributes.add("pixelize", {
    type: "number",
    min: 2,
    max: 500,
    step: 1,
    default: 100
}), PostEffectPixelate.prototype.initialize = function() {
    var e = new pc.PixelatePostEffect(this.app.graphicsDevice),
        i = this.entity.camera.postEffects;
    i.addEffect(e), this.on("enable", (function() {
        i.addEffect(e, !1)
    })), this.on("disable", (function() {
        i.removeEffect(e)
    })), this.on("attr:pixelize", (function(i, t) {
        e.pixelize = i
    }))
};

function BrightnessContrastEffect(t) {
    pc.PostEffect.call(this, t), this.shader = new pc.Shader(t, {
        attributes: {
            aPosition: pc.SEMANTIC_POSITION
        },
        vshader: ["attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    gl_Position = vec4(aPosition, 0.0, 1.0);", "    vUv0 = (aPosition.xy + 1.0) * 0.5;", "}"].join("\n"),
        fshader: ["precision " + t.precision + " float;", "", "uniform sampler2D uColorBuffer;", "uniform float uBrightness;", "uniform float uContrast;", "", "varying vec2 vUv0;", "", "void main() {", "    gl_FragColor = texture2D( uColorBuffer, vUv0 );", "    gl_FragColor.rgb += uBrightness;", "", "    if (uContrast > 0.0) {", "        gl_FragColor.rgb = (gl_FragColor.rgb - 0.5) / (1.0 - uContrast) + 0.5;", "    } else {", "        gl_FragColor.rgb = (gl_FragColor.rgb - 0.5) * (1.0 + uContrast) + 0.5;", "    }", "}"].join("\n")
    }), this.brightness = 0, this.contrast = 0
}
BrightnessContrastEffect.prototype = Object.create(pc.PostEffect.prototype), BrightnessContrastEffect.prototype.constructor = BrightnessContrastEffect, Object.assign(BrightnessContrastEffect.prototype, {
    render: function(t, e, s) {
        var r = this.device,
            i = r.scope;
        i.resolve("uBrightness").setValue(this.brightness), i.resolve("uContrast").setValue(this.contrast), i.resolve("uColorBuffer").setValue(t.colorBuffer), pc.drawFullscreenQuad(r, e, this.vertexBuffer, this.shader, s)
    }
});
var BrightnessContrast = pc.createScript("brightnessContrast");
BrightnessContrast.attributes.add("brightness", {
    type: "number",
    default: 0,
    min: -1,
    max: 1,
    title: "Brightness"
}), BrightnessContrast.attributes.add("contrast", {
    type: "number",
    default: 0,
    min: -1,
    max: 1,
    title: "Contrast"
}), BrightnessContrast.prototype.initialize = function() {
    this.effect = new BrightnessContrastEffect(this.app.graphicsDevice), this.effect.brightness = this.brightness, this.effect.contrast = this.contrast, this.on("attr", (function(t, e) {
        this.effect[t] = e
    }), this);
    var t = this.entity.camera.postEffects;
    t.addEffect(this.effect), this.on("state", (function(e) {
        e ? t.addEffect(this.effect) : t.removeEffect(this.effect)
    })), this.on("destroy", (function() {
        t.removeEffect(this.effect)
    }))
};
var SAMPLE_COUNT = 15;

function computeGaussian(e, t) {
    return 1 / Math.sqrt(2 * Math.PI * t) * Math.exp(-e * e / (2 * t * t))
}

function calculateBlurValues(e, t, o, r, s) {
    e[0] = computeGaussian(0, s), t[0] = 0, t[1] = 0;
    var l, i, a = e[0];
    for (l = 0, i = Math.floor(SAMPLE_COUNT / 2); l < i; l++) {
        var u = computeGaussian(l + 1, s);
        e[2 * l] = u, e[2 * l + 1] = u, a += 2 * u;
        var h = 2 * l + 1.5;
        t[4 * l] = o * h, t[4 * l + 1] = r * h, t[4 * l + 2] = -o * h, t[4 * l + 3] = -r * h
    }
    for (l = 0, i = e.length; l < i; l++) e[l] /= a
}

function BloomEffect(e) {
    pc.PostEffect.call(this, e);
    var t = {
            aPosition: pc.SEMANTIC_POSITION
        },
        o = ["attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    gl_Position = vec4(aPosition, 0.0, 1.0);", "    vUv0 = (aPosition + 1.0) * 0.5;", "}"].join("\n"),
        r = ["precision " + e.precision + " float;", "", "varying vec2 vUv0;", "", "uniform sampler2D uBaseTexture;", "uniform float uBloomThreshold;", "uniform float uBlueThreshold;", "uniform bool uRedBloom;", "uniform bool uGreenBloom;", "uniform bool uBlurBloom;", "", "void main(void)", "{", "    vec4 color = texture2D(uBaseTexture, vUv0);", "", "    float bDistanceR = 0.0;", "    float bDistanceG = 0.0;", "    float bDistanceB = 0.0;", "    if(uRedBloom) bDistanceR = ((color.r - color.g) + (color.r - color.b)) * 0.5 ;", "    if(uGreenBloom) bDistanceG = ((color.g - color.r) + (color.g - color.b)) * 0.5 ;", "    if(uBlurBloom) bDistanceB = ((color.b - color.r) + (color.b - color.g)) * 0.5 ;", "    float bDistance = (bDistanceR + bDistanceG + bDistanceB) * 0.5;", "    float bThreshold = bDistance >= uBlueThreshold ? uBloomThreshold : 1.0;", "    gl_FragColor = clamp((color - bThreshold) / (1.0 - bThreshold), 0.0, 1.0);", "}"].join("\n"),
        s = ["precision " + e.precision + " float;", "", "#define SAMPLE_COUNT " + SAMPLE_COUNT, "", "varying vec2 vUv0;", "", "uniform sampler2D uBloomTexture;", "uniform vec2 uBlurOffsets[SAMPLE_COUNT];", "uniform float uBlurWeights[SAMPLE_COUNT];", "", "void main(void)", "{", "    vec4 color = vec4(0.0);", "    for (int i = 0; i < SAMPLE_COUNT; i++)", "    {", "        color += texture2D(uBloomTexture, vUv0 + uBlurOffsets[i]) * uBlurWeights[i];", "    }", "", "    gl_FragColor = color;", "}"].join("\n"),
        l = ["precision " + e.precision + " float;", "", "varying vec2 vUv0;", "", "uniform float uBloomEffectIntensity;", "uniform sampler2D uBaseTexture;", "uniform sampler2D uBloomTexture;", "", "void main(void)", "{", "    vec4 bloom = texture2D(uBloomTexture, vUv0) * uBloomEffectIntensity;", "    vec4 base = texture2D(uBaseTexture, vUv0);", "", "    base *= (1.0 - clamp(bloom, 0.0, 1.0));", "", "    gl_FragColor = base + bloom;", "}"].join("\n");
    this.extractShader = new pc.Shader(e, {
        attributes: t,
        vshader: o,
        fshader: r
    }), this.blurShader = new pc.Shader(e, {
        attributes: t,
        vshader: o,
        fshader: s
    }), this.combineShader = new pc.Shader(e, {
        attributes: t,
        vshader: o,
        fshader: l
    }), this.targets = [], this.bloomThreshold = .25, this.blurAmount = 4, this.bloomIntensity = 1.25, this.sampleWeights = new Float32Array(SAMPLE_COUNT), this.sampleOffsets = new Float32Array(2 * SAMPLE_COUNT)
}
BloomEffect.prototype = Object.create(pc.PostEffect.prototype), BloomEffect.prototype.constructor = BloomEffect, BloomEffect.prototype._destroy = function() {
    var e;
    if (this.targets)
        for (e = 0; e < this.targets.length; e++) this.targets[e].destroyTextureBuffers(), this.targets[e].destroy();
    this.targets.length = 0
}, BloomEffect.prototype._resize = function(e) {
    var t, o = e.colorBuffer.width,
        r = e.colorBuffer.height;
    if (o !== this.width || r !== this.height)
        for (this.width = o, this.height = r, this._destroy(), t = 0; t < 2; t++) {
            var s = new pc.Texture(this.device, {
                name: "Bloom Texture" + t,
                format: pc.PIXELFORMAT_R8_G8_B8_A8,
                width: o >> 1,
                height: r >> 1,
                mipmaps: !1
            });
            s.minFilter = pc.FILTER_LINEAR, s.magFilter = pc.FILTER_LINEAR, s.addressU = pc.ADDRESS_CLAMP_TO_EDGE, s.addressV = pc.ADDRESS_CLAMP_TO_EDGE, s.name = "pe-bloom";
            var l = new pc.RenderTarget({
                name: "Bloom Render Target " + t,
                colorBuffer: s,
                depth: !1
            });
            this.targets.push(l)
        }
}, Object.assign(BloomEffect.prototype, {
    render: function(e, t, o) {
        this._resize(e);
        var r = this.device,
            s = r.scope;
        s.resolve("uBloomThreshold").setValue(this.bloomThreshold), s.resolve("uBlueThreshold").setValue(this.blueThreshold), s.resolve("uRedBloom").setValue(this.redBloom), s.resolve("uGreenBloom").setValue(this.greenBloom), s.resolve("uBlurBloom").setValue(this.blurBloom), s.resolve("uBaseTexture").setValue(e.colorBuffer), pc.drawFullscreenQuad(r, this.targets[0], this.vertexBuffer, this.extractShader), calculateBlurValues(this.sampleWeights, this.sampleOffsets, 1 / this.targets[1].width, 0, this.blurAmount), s.resolve("uBlurWeights[0]").setValue(this.sampleWeights), s.resolve("uBlurOffsets[0]").setValue(this.sampleOffsets), s.resolve("uBloomTexture").setValue(this.targets[0].colorBuffer), pc.drawFullscreenQuad(r, this.targets[1], this.vertexBuffer, this.blurShader), calculateBlurValues(this.sampleWeights, this.sampleOffsets, 0, 1 / this.targets[0].height, this.blurAmount), s.resolve("uBlurWeights[0]").setValue(this.sampleWeights), s.resolve("uBlurOffsets[0]").setValue(this.sampleOffsets), s.resolve("uBloomTexture").setValue(this.targets[1].colorBuffer), pc.drawFullscreenQuad(r, this.targets[0], this.vertexBuffer, this.blurShader), s.resolve("uBloomEffectIntensity").setValue(this.bloomIntensity), s.resolve("uBloomTexture").setValue(this.targets[0].colorBuffer), s.resolve("uBaseTexture").setValue(e.colorBuffer), pc.drawFullscreenQuad(r, t, this.vertexBuffer, this.combineShader, o)
    }
});
var Bloom = pc.createScript("bloom");
Bloom.attributes.add("bloomIntensity", {
    type: "number",
    default: 1,
    min: 0,
    title: "Intensity"
}), Bloom.attributes.add("bloomThreshold", {
    type: "number",
    default: .25,
    min: 0,
    max: 1,
    title: "Bloom Threshold"
}), Bloom.attributes.add("blueThreshold", {
    type: "number",
    default: .25,
    min: 0,
    max: 1,
    title: "Blue Threshold"
}), Bloom.attributes.add("blurAmount", {
    type: "number",
    default: 4,
    min: 1,
    title: "Blur amount"
}), Bloom.attributes.add("redBloom", {
    type: "boolean",
    title: "Red Bloom"
}), Bloom.attributes.add("greenBloom", {
    type: "boolean",
    title: "Green Bloom"
}), Bloom.attributes.add("blurBloom", {
    type: "boolean",
    title: "Blur Bloom"
}), Bloom.prototype.initialize = function() {
    this.effect = new BloomEffect(this.app.graphicsDevice), this.effect.bloomThreshold = this.bloomThreshold, this.effect.blurAmount = this.blurAmount, this.effect.bloomIntensity = this.bloomIntensity, this.effect.blueThreshold = this.blueThreshold, this.effect.redBloom = this.redBloom, this.effect.greenBloom = this.greenBloom, this.effect.blurBloom = this.blurBloom;
    var e = this.entity.camera.postEffects;
    e.addEffect(this.effect), this.on("attr", (function(e, t) {
        this.effect[e] = t
    }), this), this.on("state", (function(t) {
        t ? e.addEffect(this.effect) : e.removeEffect(this.effect)
    })), this.on("destroy", (function() {
        e.removeEffect(this.effect), this._destroy()
    }))
};
var TestButton = pc.createScript("testButton");
let __this;

function playcnavas_func(t) {
    __this ? t(__this) : console.log("defind playcanvas")
}

function playcnavas_Interactive_Move(t) {
    __this ? __this.app.root.findByName("test Screen").script.testButton.moveNumber(t) : console.log("defind playcanvas")
}

function playcnavas_Interactive_Rotation(t) {
    __this ? __this.app.root.findByName("test Screen").script.testButton.moveRotation(t) : console.log("defind playcanvas")
}
TestButton.attributes.add("maxPage", {
    type: "number",
    title: "최대 페이지"
}), TestButton.prototype.initialize = function() {
    __this = this, this.nowPageNumber = 1, this.isRotation = !1, this.isBack = !1, this.nowPageEntity = this.app.root.findByName("p1"), this.nowPageTitle = this.app.root.findByName("Page1 Title"), this.scene = this.app.root.findByName("Scene_rotate"), this.sceneAni = this.scene.anim, this.app.root.findByName("left_Button").button.on("click", (function() {
        this.isRotation || (this.sceneAni.setTrigger("rot1"), this.isRotation = !0, this.pageMove(-1), this.isBack = !this.isBack)
    }), this), this.app.root.findByName("right_Button").button.on("click", (function() {
        this.isRotation || (this.sceneAni.setTrigger("rot1"), this.isRotation = !0, this.pageMove(1), this.isBack = !this.isBack)
    }), this), this.app.root.findByName("back_Button").button.on("click", (function() {
        this.app.root.findByName("Back Button Group").enabled = !1, this.app.root.findByName("Button Group").enabled = !0, this.app.root.findByName("Path Camera").enabled = !1
    }), this), this.sceneAni.on("end-animation-rot1", (function() {
        this.pageMoveEnd()
    }), this), this.sceneAni.on("end-animation-rot2", (function() {
        this.pageMoveEnd()
    }), this)
}, TestButton.prototype.pageMove = function(t) {
    this.nowPageNumber += t, this.nowPageNumber <= 0 ? this.nowPageNumber = this.maxPage : this.nowPageNumber > this.maxPage && (this.nowPageNumber = 1), console.log(this.nowPageTitle), this.nowPageTitle.enabled = !1, this.nowPageTitle = this.app.root.findByName("Page" + this.nowPageNumber + " Title"), this.nowPageTitle.enabled = !0;
    let i = this.app.root.findByName("p" + this.nowPageNumber),
        e = this.isBack ? this.app.root.findByName("Point_1") : this.app.root.findByName("Point_2");
    i.enabled = !0, i.setLocalPosition(e.getLocalPosition()), i.setLocalEulerAngles(e.getLocalEulerAngles())
}, TestButton.prototype.pageMoveEnd = function() {
    this.isRotation = !1, this.nowPageEntity.enabled = !1, this.nowPageEntity = this.app.root.findByName("p" + this.nowPageNumber)
}, TestButton.prototype.moveRotation = function(t) {
    if (this.isRotation) return;
    let i = t ? -1 : 1;
    this.sceneAni.setTrigger("rot1"), this.isRotation = !0, this.pageMove(i), this.isBack = !this.isBack
}, TestButton.prototype.moveNumber = function(t) {
    this.nowPageNumber != t && (this.isRotation || (this.nowPageNumber = t, this.sceneAni.setTrigger("rot1"), this.isRotation = !0, this.pageMove(0), this.isBack = !this.isBack))
};
pc.script.createLoadingScreen((function(e) {
    var t, a;
    t = ["body {", "    background-color: #000000;", "}", "", "#application-splash-wrapper {", "    position: absolute;", "    top: 0;", "    left: 0;", "    height: 100%;", "    width: 100%;", "    background-color: #000000;", "        display: flex;", "        align-items: center;", "        justify-content: center;", "}", "", "#application-splash {", "    position: initial;", "    top: calc(50% - 28px);", "    width: 264px;", "    left: calc(50% - 132px);", "}", "", "#application-splash img {", "    width: 100%;", "}", "", "#progress-bar-container {", "    margin: 20px auto 0 auto;", "    height: 2px;", "    width: 100%;", "    background-color: #1d292c;", "}", "", "#progress-bar {", "    width: 0%;", "    height: 100%;", "    background-color: #fff;", "}", "", "@media (max-width: 480px) {", "    #application-splash {", "        width: 170px;", "        left: calc(50% - 85px);", "    }", "}"].join("\n"), (a = document.createElement("style")).type = "text/css", a.styleSheet ? a.styleSheet.cssText = t : a.appendChild(document.createTextNode(t)), document.head.appendChild(a),
        function() {
            var e = document.createElement("div");
            e.id = "application-splash-wrapper", document.body.appendChild(e);
            var t = document.createElement("div");
            t.id = "application-splash", e.appendChild(t), t.style.display = "none";
            var a = document.createElement("img");
            a.src = "https://wolbong.uokdc.com/assets/test/logo_04.png", t.appendChild(a), a.onload = function() {
                t.style.display = "block"
            };
            var n = document.createElement("div");
            n.id = "progress-bar-container", t.appendChild(n);
            var o = document.createElement("div");
            o.id = "progress-bar", n.appendChild(o)
        }(), e.on("preload:end", (function() {
            e.off("preload:progress")
        })), e.on("preload:progress", (function(e) {
            var t = document.getElementById("progress-bar");
            t && (e = Math.min(1, Math.max(0, e)), t.style.width = 100 * e + "%")
        })), e.on("start", (function() {
            var e = document.getElementById("application-splash-wrapper");
            e.parentElement.removeChild(e)
        }))
}));
var ParticleOn = pc.createScript("particleOn");
ParticleOn.attributes.add("name", {
    type: "string"
}), ParticleOn.prototype.initialize = function() {
    this.entity.collision.on("triggerenter", this.onCollisionStart, this), this.entity.collision.on("triggerleave", this.onCollisionEnd, this)
}, ParticleOn.prototype.onCollisionStart = function(t) {
    const i = this;
    t.tags._list[0].includes("Particle") && (this.particleInterval = setInterval((function() {
        i.entity.findByName("Particle_" + Math.ceil(3 * Math.random())).particlesystem.reset(), i.entity.findByName("Particle_" + Math.ceil(3 * Math.random())).particlesystem.play()
    }), 250 + 10 * Math.random() * 100))
}, ParticleOn.prototype.onCollisionEnd = function() {
    clearInterval(this.particleInterval)
}, ParticleOn.prototype.update = function(t) {};
var InteractionVr = pc.createScript("interactionVr");
InteractionVr.attributes.add("contentEntity", {
    type: "entity"
}), InteractionVr.prototype.initialize = function() {
    this.entity.button.on("click", (function() {
        console.log("클릭"), this.content()
    }), this), "interaction_Content_2" == this.contentEntity.name && this.contentEntity.findByName("YES_Btn").button.on("click", (function() {
        this.test()
    }), this)
}, InteractionVr.prototype.update = function(t) {}, InteractionVr.prototype.content = function() {
    let t = this.contentEntity;
    t.enabled = !0, clearTimeout(this.contestEnabled), this.contestEnabled = setTimeout((function() {
        t.enabled = !1
    }), 1e3)
}, InteractionVr.prototype.test = function() {
    this.app.root.findByName("Back Button Group").enabled = !0, this.app.root.findByName("Button Group").enabled = !1, this.app.root.findByName("Path Point(Cam)").setPosition(this.app.root.findByName("Camera").getPosition()), this.app.root.findByName("Path Point(Cam)").setEulerAngles(this.app.root.findByName("Camera").getEulerAngles()), this.app.root.findByName("Path Point(End)").setEulerAngles(this.app.root.findByName("Camera").getEulerAngles()), this.app.root.findByName("Camera").script.cameraPath.createPath(), this.app.root.findByName("Camera").script.cameraPath.time = 0, this.app.root.findByName("Camera").script.cameraPath.flyingThrough = !0, this.app.root.findByName("Camera").script.mouseInput.enabled = !1
};
var LookAtcam = pc.createScript("lookAtcam");
LookAtcam.prototype.initialize = function() {
    this.camera = this.app.root.findByName("Camera Parent").findByName("Camera")
}, LookAtcam.prototype.update = function(t) {
    this.entity.lookAt(this.camera.getPosition())
};
var CameraPath = pc.createScript("cameraPath");
CameraPath.attributes.add("pathRoot", {
    type: "entity",
    title: "Path Root"
}), CameraPath.attributes.add("duration", {
    type: "number",
    default: 10,
    title: "Duration Secs"
}), CameraPath.attributes.add("startTime", {
    type: "number",
    default: 0,
    title: "Start Time (Secs)",
    description: "Start the path from a specific point in time"
}), CameraPath.prototype.initialize = function() {
    this.createPath(), this.on("attr:pathRoot", (function(t, i) {
        t && (this.createPath(), this.time = 0)
    })), this.on("attr:time", (function(t, i) {
        this.time = pc.math.clamp(this.startTime, 0, this.duration)
    })), this.time = pc.math.clamp(this.startTime, 0, this.duration), this.lookAt = new pc.Vec3, this.up = new pc.Vec3, this.flyingThrough = !0, this.speed = .01, this.minSpeed = .02, this.slowTime = .25, this.startTimer = 0
}, CameraPath.prototype.update = function(t) {
    var i;
    if (this.flyingThrough) {
        if (this.time += t * this.speed, this.time > this.duration) {
            let t = this.entity.getEulerAngles();
            this.entity.script.mouseInput.enabled = !0, this.entity.script.orbitCamera.enabled = !0, this.entity.script.orbitCamera.setAngle(t), this.app.root.findByName("VR_Content").enabled = !0
        }
        i = this.time / this.duration, this.time < 1 && this.speed <= 1 && (this.speed += this.speed * t * 2), this.time > this.duration - this.slowTime && this.speed > this.minSpeed && (this.speed = pc.math.lerp(this.speed, this.duration - this.time, .1))
    }
    this.entity.setPosition(this.px.value(i), this.py.value(i), this.pz.value(i)), this.lookAt.set(this.tx.value(i), this.ty.value(i), this.tz.value(i)), this.up.set(this.ux.value(i), this.uy.value(i), this.uz.value(i)), this.entity.lookAt(this.lookAt, this.up)
}, CameraPath.prototype.createPath = function() {
    var t = pc.CURVE_CARDINAL;
    this.px = new pc.Curve, this.px.type = t, this.py = new pc.Curve, this.py.type = t, this.pz = new pc.Curve, this.pz.type = t, this.tx = new pc.Curve, this.tx.type = t, this.ty = new pc.Curve, this.ty.type = t, this.tz = new pc.Curve, this.tz.type = t, this.ux = new pc.Curve, this.ux.type = t, this.uy = new pc.Curve, this.uy.type = t, this.uz = new pc.Curve, this.uz.type = t;
    var e = this.pathRoot.children,
        s = 0,
        h = [],
        a = new pc.Vec3;
    for (h.push(0), i = 1; i < e.length; i++) {
        var r = e[i - 1],
            n = e[i];
        a.sub2(r.getPosition(), n.getPosition()), s += a.length(), h.push(s)
    }
    for (i = 0; i < e.length; i++) {
        var p = h[i] / s,
            u = e[i],
            o = u.getPosition();
        this.px.add(p, o.x), this.py.add(p, o.y), this.pz.add(p, o.z);
        var d = o.clone().add(u.forward);
        this.tx.add(p, d.x), this.ty.add(p, d.y), this.tz.add(p, d.z);
        var c = u.up;
        this.ux.add(p, c.x), this.uy.add(p, c.y), this.uz.add(p, c.z)
    }
}, CameraPath.prototype.addUi = function() {
    var t = this,
        i = document.createElement("style");
    document.head.appendChild(i), i.innerHTML = this.css.resource || "", this.div = document.createElement("div"), this.div.classList.add("container"), this.div.innerHTML = this.html.resource || "", document.body.appendChild(this.div), this.resumeFlythroughButton = this.div.querySelector(".button"), this.pathSlider = this.div.querySelector(".slider"), this.resumeFlythroughButton.addEventListener("click", (function() {
        t.flyingThrough = !0
    })), this.pathSlider.addEventListener("input", (function() {
        t.flyingThrough = !1, t.time = t.pathSlider.value / t.pathSlider.max * t.duration
    }))
};
var ClickToHighlight = pc.createScript("clickToHighlight");
ClickToHighlight.prototype.initialize = function() {
    this.cameraEntity = this.entity, this.previous = void 0, this.moveCheck = this.app.root.findByName("test Screen").script.testButton, this.mouseOn = !1, this.app.touch ? this.app.touch.on(pc.EVENT_TOUCHSTART, this.touchStart, this) : (this.app.mouse.on(pc.EVENT_MOUSEMOVE, this.mouseDown, this), this.app.mouse.on(pc.EVENT_MOUSEDOWN, this.mouseClick, this), this.app.mouse.on(pc.EVENT_MOUSEUP, this.mouseUp, this))
}, ClickToHighlight.prototype.mouseDown = function(t) {
    this.mouseOn ? this.doRaycast_Ground(t) : this.doRaycast(t)
}, ClickToHighlight.prototype.touchStart = function(t) {
    1 == t.touches.length && this.doRaycast(t.touches[0]), t.event.preventDefault()
}, ClickToHighlight.prototype.doRaycast = function(t) {
    var i = this.cameraEntity.getPosition(),
        s = this.cameraEntity.camera.screenToWorld(t.x, t.y, this.cameraEntity.camera.farClip),
        e = this.app.systems.rigidbody.raycastFirst(i, s);
    if (e) {
        if (e.entity.tags.has("Ground")) return void(this.previous && (this.app.fire("Ermis:objectOutline:remove", this.previous), this.previous = void 0));
        this.app.fire("Ermis:objectOutline:add", e.entity), this.previous && this.previous._guid !== e.entity._guid && this.app.fire("Ermis:objectOutline:remove", this.previous), this.previous = e.entity
    } else this.previous && (this.app.fire("Ermis:objectOutline:remove", this.previous), this.previous = void 0)
}, ClickToHighlight.prototype.mouseClick = function() {
    if (this.previous && this.previous.tags.has("moveStickman")) {
        this.moveCheck.isRotation = !0, this.previous.collision.enabled = !1, this.mouseOn = !0;
        let t = this.previous.parent.findByName("Particle_Confeti");
        t && (t.particlesystem.reset(), t.particlesystem.play())
    }
}, ClickToHighlight.prototype.mouseUp = function() {
    if (this.previous && this.previous.tags.has("moveStickman")) {
        this.moveCheck.isRotation = !1, this.previous.collision.enabled = !0, this.mouseOn = !1;
        let t = this.previous.parent.findByName("Particle_Confeti");
        t && (t.particlesystem.reset(), t.particlesystem.play())
    }
}, ClickToHighlight.prototype.doRaycast_Ground = function(t) {
    var i = this.cameraEntity.getPosition(),
        s = this.cameraEntity.camera.screenToWorld(t.x, t.y, this.cameraEntity.camera.farClip),
        e = this.app.systems.rigidbody.raycastAll(i, s);
    for (let t of e) this.previous && t.entity.tags.has("Ground") && this.previous.parent.setPosition(t.point)
};
var ApplyOutline = pc.createScript("applyOutline");
ApplyOutline.attributes.add("color", {
    type: "rgba"
}), ApplyOutline.attributes.add("thickness", {
    type: "number",
    default: 1,
    min: 1,
    max: 10,
    precision: 0,
    title: "Thickness"
}), ApplyOutline.prototype.initialize = function() {
    this.vec = new pc.Vec3, this.prepare(), window.addEventListener("resize", this.onResize.bind(this)), this.app.on("Ermis:objectOutline:add", (function(e) {
        if (e && e.render && -1 === e.render.layers.indexOf(this.outlineLayer.id)) {
            var t = e.render.layers.slice();
            t.push(this.outlineLayer.id), e.render.layers = t
        }
    }), this), this.app.on("Ermis:objectOutline:remove", (function(e) {
        if (e.render) {
            var t = e.render.layers.indexOf(this.outlineLayer.id);
            if (e && e.render && t > -1) {
                var i = e.render.layers.slice();
                i.splice(t, 1), e.render.layers = i
            }
        }
    }), this)
}, ApplyOutline.prototype.prepare = function() {
    this.texture = new pc.Texture(this.app.graphicsDevice, {
        width: this.app.graphicsDevice.width,
        height: this.app.graphicsDevice.height,
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        autoMipmap: !0,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR
    }), this.renderTarget = new pc.RenderTarget({
        colorBuffer: this.texture,
        depth: !0,
        samples: 8
    }), this.worldLayer = this.app.scene.layers.getLayerByName("World"), this.outlineLayer = this.app.scene.layers.getLayerByName("OutlineLayer"), this.outlineLayer.renderTarget = this.renderTarget, this.outlineCamera = new pc.Entity, this.outlineCamera.addComponent("camera", {
        clearColor: new pc.Color(0, 0, 0, 0),
        layers: [this.outlineLayer.id],
        fov: this.app.root.findByName("Camera").camera.fov
    }), this.app.root.addChild(this.outlineCamera), this.outlineCamera.camera.priority = 0, this.outline = new pc.OutlineEffect(this.app.graphicsDevice, this.thickness), this.outline.color = new pc.Color(0, 0, 1, 1), this.outline.texture = this.texture, this.entity.camera.postEffects.addEffect(this.outline)
}, ApplyOutline.prototype.onResize = function() {
    this.entity.camera.postEffects.removeEffect(this.outline), this.app.scene.layers.remove(this.outlineLayer), this.texture.destroy(), this.texture = new pc.Texture(this.app.graphicsDevice, {
        width: this.app.graphicsDevice.width,
        height: this.app.graphicsDevice.height,
        format: pc.PIXELFORMAT_R8_G8_B8_A8,
        autoMipmap: !0,
        minFilter: pc.FILTER_LINEAR,
        magFilter: pc.FILTER_LINEAR
    }), this.renderTarget.destroy(), this.renderTarget = new pc.RenderTarget(this.app.graphicsDevice, this.texture, {
        depth: !0
    }), this.outlineLayer.renderTarget = this.renderTarget, this.app.scene.layers.insert(this.outlineLayer, 0), this.outline.texture = this.texture, this.entity.camera.postEffects.addEffect(this.outline)
}, ApplyOutline.prototype.update = function(e) {
    var t = this.entity.getWorldTransform();
    this.outlineCamera.setPosition(t.getTranslation()), this.outlineCamera.setEulerAngles(t.getEulerAngles()), this.outlineCamera.setLocalScale(t.getScale()), this.outlineCamera.camera.horizontalFov = !(this.app.graphicsDevice.width > this.app.graphicsDevice.height), this.outline.color.copy(this.color)
};
Object.assign(pc, function() {
    var OutlineEffect = function(e, t) {
        pc.PostEffect.call(this, e), this.shader = new pc.Shader(e, {
            attributes: {
                aPosition: pc.SEMANTIC_POSITION
            },
            vshader: ["attribute vec2 aPosition;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    gl_Position = vec4(aPosition, 0.0, 1.0);", "    vUv0 = (aPosition.xy + 1.0) * 0.5;", "}"].join("\n"),
            fshader: ["precision " + e.precision + " float;", "", "#define THICKNESS " + t.toFixed(0), "uniform float uWidth;", "uniform float uHeight;", "uniform vec4 uOutlineCol;", "uniform sampler2D uColorBuffer;", "uniform sampler2D uOutlineTex;", "", "varying vec2 vUv0;", "", "void main(void)", "{", "    vec4 texel1 = texture2D(uColorBuffer, vUv0);", "    float sample0 = texture2D(uOutlineTex, vUv0).a;", "    float outline = 0.0;", "    if (sample0==0.0)", "    {", "        for (int x=-THICKNESS;x<=THICKNESS;x++)", "        {", "            for (int y=-THICKNESS;y<=THICKNESS;y++)", "            {    ", "                float sample=texture2D(uOutlineTex, vUv0+vec2(float(x)/uWidth, float(y)/uHeight)).a;", "                if (sample>0.0)", "                {", "                    outline=1.0;", "                }", "            }", "        } ", "    }", "    gl_FragColor = mix(texel1, uOutlineCol, outline * uOutlineCol.a);", "}"].join("\n")
        }), this.color = new pc.Color(1, 1, 1, 1), this.texture = new pc.Texture(e), this.texture.name = "pe-outline"
    };
    (OutlineEffect.prototype = Object.create(pc.PostEffect.prototype)).constructor = OutlineEffect;
    var e = [0, 0, 0, 0];
    return Object.assign(OutlineEffect.prototype, {
        render: function(t, i, o) {
            var r = this.device,
                u = r.scope;
            u.resolve("uWidth").setValue(t.width), u.resolve("uHeight").setValue(t.height), e[0] = this.color.r, e[1] = this.color.g, e[2] = this.color.b, e[3] = this.color.a, u.resolve("uOutlineCol").setValue(e), u.resolve("uColorBuffer").setValue(t.colorBuffer), u.resolve("uOutlineTex").setValue(this.texture), pc.drawFullscreenQuad(r, i, this.vertexBuffer, this.shader, o)
        }
    }), {
        OutlineEffect: OutlineEffect
    }
}());
var Outline = pc.createScript("outline");
Outline.attributes.add("color", {
    type: "rgb",
    default: [.5, .5, .5, 1],
    title: "Color"
}), Outline.attributes.add("texture", {
    type: "asset",
    assetType: "texture",
    title: "Texture"
}), Outline.prototype.initialize = function() {
    this.effect = new pc.OutlineEffect(this.app.graphicsDevice), this.effect.color = this.color, this.effect.texture = this.texture.resource;
    var e = this.entity.camera.postEffects;
    e.addEffect(this.effect), this.on("state", (function(t) {
        t ? e.addEffect(this.effect) : e.removeEffect(this.effect)
    })), this.on("destroy", (function() {
        e.removeEffect(this.effect)
    })), this.on("attr:color", (function(e) {
        this.effect.color = e
    }), this), this.on("attr:texture", (function(e) {
        this.effect.texture = e ? e.resource : null
    }), this)
};
var Ribbon = pc.createScript("ribbon");
Ribbon.attributes.add("lifetime", {
    type: "number",
    default: .5
}), Ribbon.attributes.add("xoffset", {
    type: "number",
    default: -.8
}), Ribbon.attributes.add("yoffset", {
    type: "number",
    default: 1
}), Ribbon.attributes.add("height", {
    type: "number",
    default: .4
});
var MAX_VERTICES = 600,
    VERTEX_SIZE = 4;
Ribbon.prototype.create = function(e) {
    this.timer = 0, this.node = null, this.vertices = [], this.vertexData = new Float32Array(MAX_VERTICES * VERTEX_SIZE), this.entity.model = null
}, Ribbon.prototype.initialize = function() {
    this.create();
    var e = {
            attributes: {
                aPositionAge: pc.SEMANTIC_POSITION
            },
            vshader: ["attribute vec4 aPositionAge;", "", "uniform mat4 matrix_viewProjection;", "uniform float trail_time;", "", "varying float vAge;", "", "void main(void)", "{", "    vAge = trail_time - aPositionAge.w;", "    gl_Position = matrix_viewProjection * vec4(aPositionAge.xyz, 1.0);", "}"].join("\n"),
            fshader: ["precision mediump float;", "", "varying float vAge;", "", "uniform float trail_lifetime;", "", "vec3 rainbow(float x)", "{", "float level = floor(x * 6.0);", "float r = float(level <= 2.0) + float(level > 4.0) * 0.5;", "float g = max(1.0 - abs(level - 2.0) * 0.5, 0.0);", "float b = (1.0 - (level - 4.0) * 0.5) * float(level >= 4.0);", "return vec3(r, g, b);", "}", "void main(void)", "{", "    gl_FragColor = vec4(rainbow(vAge / trail_lifetime), (1.0 - (vAge / trail_lifetime)) * 0.5);", "}"].join("\n")
        },
        t = new pc.Shader(this.app.graphicsDevice, e),
        i = new pc.scene.Material;
    i.setShader(t), i.setParameter("trail_time", 0), i.setParameter("trail_lifetime", this.lifetime), i.cull = pc.CULLFACE_NONE, i.blend = !0, i.blendSrc = pc.BLENDMODE_SRC_ALPHA, i.blendDst = pc.BLENDMODE_ONE_MINUS_SRC_ALPHA, i.blendEquation = pc.BLENDEQUATION_ADD, i.depthWrite = !1;
    var r = new pc.VertexFormat(this.app.context.graphicsDevice, [{
            semantic: pc.SEMANTIC_POSITION,
            components: 4,
            type: pc.ELEMENTTYPE_FLOAT32
        }]),
        a = new pc.VertexBuffer(this.app.context.graphicsDevice, r, MAX_VERTICES * VERTEX_SIZE, pc.USAGE_DYNAMIC),
        o = new pc.scene.Mesh;
    o.vertexBuffer = a, o.indexBuffer[0] = null, o.primitive[0].type = pc.PRIMITIVE_TRISTRIP, o.primitive[0].base = 0, o.primitive[0].count = 0, o.primitive[0].indexed = !1;
    var s = new pc.scene.GraphNode,
        n = new pc.scene.MeshInstance(s, o, i);
    n.layer = pc.scene.LAYER_WORLD, n.updateKey(), this.entity.model = new pc.scene.Model, this.entity.model.graph = s, this.entity.model.meshInstances.push(n), this.model = this.entity.model, this.setNode(this.entity)
}, Ribbon.prototype.reset = function() {
    this.timer = 0, this.vertices = []
}, Ribbon.prototype.spawn = function() {
    var e = this.node,
        t = e.getPosition(),
        i = e.up.clone().scale(this.height),
        r = this.xoffset,
        a = this.yoffset;
    this.vertices.unshift({
        spawnTime: this.timer,
        vertexPair: [t.x + i.x * r, t.y + i.y * r, t.z + i.z * r, t.x + i.x * a, t.y + i.y * a, t.z + i.z * a]
    })
}, Ribbon.prototype.clearOld = function() {
    for (var e = this.vertices.length - 1; e >= 0; e--) {
        var t = this.vertices[e];
        if (!(this.timer - t.spawnTime >= this.lifetime)) return;
        this.vertices.pop()
    }
}, Ribbon.prototype.copyToArrayBuffer = function() {
    for (var e = 0; e < this.vertices.length; e++) {
        var t = this.vertices[e];
        this.vertexData[8 * e + 0] = t.vertexPair[0], this.vertexData[8 * e + 1] = t.vertexPair[1], this.vertexData[8 * e + 2] = t.vertexPair[2], this.vertexData[8 * e + 3] = t.spawnTime, this.vertexData[8 * e + 4] = t.vertexPair[3], this.vertexData[8 * e + 5] = t.vertexPair[4], this.vertexData[8 * e + 6] = t.vertexPair[5], this.vertexData[8 * e + 7] = t.spawnTime
    }
}, Ribbon.prototype.updateNumActive = function() {
    this.model.meshInstances[0].mesh.primitive[0].count = 2 * this.vertices.length
}, Ribbon.prototype.update = function(e) {
    if (this.timer += e, this.model.meshInstances[0].material.setParameter("trail_time", this.timer), this.clearOld(), this.spawn(), this.vertices.length > 1) {
        this.copyToArrayBuffer(), this.updateNumActive();
        var t = this.model.meshInstances[0].mesh.vertexBuffer;
        new Float32Array(t.lock()).set(this.vertexData), t.unlock(), this.app.scene.containsModel(this.model) || (console.log("Added model"), this.app.scene.addModel(this.model))
    } else this.app.scene.containsModel(this.model) && (console.log("Removed model"), this.app.scene.removeModel(this.model))
}, Ribbon.prototype.setNode = function(e) {
    this.node = e
};