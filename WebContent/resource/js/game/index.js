
$(document).ready(function(){
    threeStart();
});
function threeStart() {
    initScreen();
    initThree();
    initCamera();
    initScene();
    initLight();
    initObject();
    initMouseEvent();
    loop();
}

function initScreen(){
    $("#canvas-frame").height($(window).height()-100);
    document.oncontextmenu=function(){return false;};
}

var FORWARD = 87, BACKWORD = 83, LEFT = 65, RIGHT = 68;
var moveX = 0, moveY = 0, moveZ = 0;
function initMouseEvent(){
    $("#canvas-frame").mousemove(function(){
    });
    $(document).keydown(function(){
        //w 87, s 83, a 65, d 68
        var step = 2;
        if(event.keyCode == FORWARD){
            moveX = -step;
        }else if(event.keyCode == BACKWORD){
            moveX = +step;
        }else if(event.keyCode == LEFT){
            moveY = -step;
        }else if(event.keyCode == RIGHT){
            moveY = +step;
        }
    }).keyup(function(){
        if(event.keyCode == FORWARD || event.keyCode == BACKWORD){
            moveX = 0;
        }else if(event.keyCode == LEFT || event.keyCode == RIGHT){
            moveY = 0;
        }
    });
}

var renderer;
function initThree() {
    width = document.getElementById('canvas-frame').clientWidth;
    height = document.getElementById('canvas-frame').clientHeight;
    renderer = new THREE.WebGLRenderer({
        antialias : true
    });
    renderer.setSize(width, height);
    document.getElementById('canvas-frame').appendChild(renderer.domElement);
    renderer.setClearColor(0xFFFFFF, 1.0);
    renderer.shadowMapEnabled = true;
}

var camera;
function initCamera() {
    camera = new THREE.PerspectiveCamera(45, width / height, 1, 10000);
    camera.position.x = 300;
    camera.position.y = 0;
    camera.position.z = 100;
    camera.up.x = 0;
    camera.up.y = 0;
    camera.up.z = 1;
    camera.lookAt({
        x : 0,
        y : 0,
        z : 0
    });
}
var scene;
function initScene() {
    scene = new THREE.Scene();
    
}
var light;
function initLight() {
    light = new THREE.DirectionalLight(0xffffff, 1.0, 0);
    light.position.set(100, 100, 200);
    light.castShadow = true;
    scene.add(light);
    
    light2 = new THREE.AmbientLight(0x454545);
    scene.add(light2); 
}
var cube;
var plane;
function initObject() {
    cube = new THREE.Mesh(new THREE.CubeGeometry(50, 50, 50), 
            new THREE.MeshLambertMaterial({
                color: 0xff2200, 
                ambient:0xFF2200
            })
    );
    scene.add(cube);
    cube.position.set(0, 100, 25);
    
    plane = new THREE.Mesh(
            new THREE.PlaneGeometry(800,800),                
            new THREE.MeshLambertMaterial({color: 0xaaaaaa})
          );
    scene.add(plane);
    plane.receiveShadow = true;
    plane.position.set(0,0,0);   
}
function loop() {
    //cube.rotation.set( t/80, t/150, t/120 );
    cube.rotation.z += 360*Math.PI/180/60 /10
    camera.position.x += moveX;
    camera.position.y += moveY;
//    camera.position.set( 400*Math.cos(t/120), 300*Math.sin(t/200), 20*Math.cos(t/50));
//    camera.lookAt( {x:0, y:0, z:0 } );   
//    
    renderer.clear();
    renderer.render(scene, camera);
    window.requestAnimationFrame(loop);
  } 
