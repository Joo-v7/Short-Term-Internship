var Test = pc.createScript('test');

// initialize code called once per entity
Test.prototype.initialize = function() {
    this.entity.button.on('click' , function(){
        console.log("클릭");
    });
};

// update code called every frame
Test.prototype.update = function(dt) {

};

// swap method called for script hot-reloading
// inherit your script state here
// Test.prototype.swap = function(old) { };

// to learn more about script anatomy, please read:
// https://developer.playcanvas.com/en/user-manual/scripting/