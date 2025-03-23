/*
 * Copyright (c) 2011 SimplyCast
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

CKEDITOR.plugins.add('imagemap',
    {
      init: function(editor)
      {
        var pluginName = 'imagemap';
        editor.ui.addButton( 'ImageMap',
            {
              label : "Edit Imagemap",
              command : pluginName,
              icon: this.path+'imagemap.gif',
              click: function(editor){
                //var jqeditor = editor; createFromHtml

                var selected = editor.getSelection().getSelectedElement();
                if(selected === null ){
                  alert('이미지를 선택하여 주십시오.');
                  return false;
                }

                $(editor.element.$).next().css('position','relative');
                $(editor.element.$).next().prepend($("<div id='map_modal' class='modal'>"));
                //$("#map_modal").css({'z-index':99,'height':'900px','max-height':'none','top':0});
                $("#map_modal").load('/assets/editor/ckeditor/plugins/imagemap/template.html');

                selected = new CKEDITOR.dom.element( selected.find("img").$[0] );
                if(selected && selected.is("img")){
                  if(null==selected.getAttribute("id")) selected.setAttribute('id','xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) { //add UUID for id
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                  }));
                  setTimeout(function(){
                    initImageMapEditor(editor,selected);
                  },500);

                }else{
                  alert("이미지를 선택해주세요!");
                }
              }
            });


        //console.log("Added imagemap plugin");
      }
    });


var processingInstance;

var selected_idx = null;
var selected = null;
var target = null;
var hasMap =false;
var mapName =null;
var area = {};
var b=0.55191502449;
var imgWidth = null;
var imgHeight = null;
var damages_canvas = null;
var ctx_damages = null;
var circle_rect = null;
var markerColor = "#f00";
var g_rect = {}; // startX, startY, w, h
var g_is_mousedown = false;
var g_is_start = false;
var g_type = null;
var g_canvas_scale = 1;
var index = 0;
var editors = null;
function initImageMapEditor(editor,figure){

  var src = $(figure.$).attr('src');
  $("#map_modal img").attr('src', src);
  $("#map_modal").show();
  $('#map_modal').css('height', '100%');
  $('#map_modal').css('width', '80%');

  editors = editor;
  target = null;
  hasMap =false;
  mapName =null;
  area = {};
  b=0.55191502449;
  imgWidth = $("#imageMapCanvasDiv img")[0].naturalWidth;
  imgHeight = $("#imageMapCanvasDiv img")[0].naturalHeight;
  $("#imageMapCanvasDiv img")[0].setAttribute("usemap","#"+mapName);
  damages_canvas = document.getElementById("damages-area");
  damages_canvas.setAttribute("width",$("#imageMapCanvasDiv img").width());
  damages_canvas.setAttribute("height",$("#imageMapCanvasDiv img").height());
  ctx_damages = damages_canvas.getContext("2d");
  circle_rect = damages_canvas.getBoundingClientRect();
  markerColor = "#f00";
  g_rect = {}; // startX, startY, w, h
  g_is_mousedown = false;
  g_is_start = false;
  g_type = null;
 g_canvas_scale = $("#imageMapCanvasDiv img").width()/imgWidth;
  index = 0;


  init(editor,figure);
}

function init(editor,image)
{
  target = image;
  mapName = image.getAttribute('usemap');
  if(mapName!=undefined) mapName=mapName.replace("#","");
  hasMap=false;
  if(mapName==undefined) {
    mapName =  Math.random().toString(36).substr(2,11);
  } else {
    maps = image.getDocument().getElementsByTag("map").$;

    ctx_damages.clearRect(0,0,damages_canvas.width,damages_canvas.height);
    ctx_damages.beginPath();
    ctx_damages.strokeStyle =markerColor;
    ctx_damages.lineWidth = 1;

    for (i = 0; i < maps.length; i++) { // searching existing maps
      if (maps[i].getAttribute('name')==mapName.replace("#","")){ // found our map
        //console.log("setting map to: "+maps[i].getAttribute('name'));
        map = maps[i];
        for (ii = 0; ii < map.children.length; ii++){
          var title = map.childNodes[ii].getAttribute('title');
          var shape = map.childNodes[ii].getAttribute('shape');
          var coords = map.childNodes[ii].getAttribute('coords')
          var alt = map.childNodes[ii].getAttribute('alt');
          var href = map.childNodes[ii].getAttribute("href");
          var target2 = map.childNodes[ii].getAttribute("target");

          area['href'] = href;
          area['title'] = title;
          area['alt'] = alt;
          area['target'] = target2;
          area['coords'] = coords;
          area['shape'] = shape;


          $("#imageMapAreas").append(new Option(title,JSON.stringify(area)));
          var coordsArr = coords.split(",")
          if(shape == 'circle'){
            ctx_damages.arc(coordsArr[0], coordsArr[1], coordsArr[2], 0 ,Math.PI *2);
            ctx_damages.stroke(); //테두리
          }
          else{
            ctx_damages.strokeRect(coordsArr[0], coordsArr[1], coordsArr[2], coordsArr[3]);
          }
        }
        hasMap=true; // only now do have a map.
      }
    }
  }

  if( !hasMap){
    map=new CKEDITOR.dom.element( 'map' );
    //console.log("Generating image map name:",mapName);
    map.setAttribute('name', mapName); // set name
  }

  damages_canvas.addEventListener('mousedown',function(e){
    if(!g_is_start) return false;
    console.log(e);
    console.log(circle_rect);
    g_rect.startX = e.pageX - circle_rect.left;
    g_rect.startY = e.pageY - circle_rect.top;
    g_is_mousedown = true;
  }, false);
  damages_canvas.addEventListener('mouseup', function(e){
    if(!g_is_start) return false;
    g_is_mousedown = false;
  }, false);
  damages_canvas.addEventListener('mousemove',function(e){
    if(!g_is_start) return false;
    if(g_is_mousedown) {
      g_rect.w = (e.pageX - circle_rect.left) - g_rect.startX;
      g_rect.h = (e.pageY - circle_rect.top) - g_rect.startY;

      ctx_damages.clearRect(0,0,damages_canvas.width,damages_canvas.height);
      ctx_damages.beginPath();
      ctx_damages.strokeStyle =markerColor;
      ctx_damages.lineWidth = 1;
      $.each($("#imageMapAreas option"),function(key,obj){
        if(selected_idx != null &&  key == selected_idx){
          return
        }
        var option_area =  $.parseJSON( $(obj).val());
        var coords = option_area.coords.split(",")

        if(option_area.shape == 'circle'){
          ctx_damages.arc(coords[0], coords[1], coords[2], 0 ,Math.PI *2);
          ctx_damages.stroke(); //테두리
        }
        else{
          ctx_damages.strokeRect(coords[0], coords[1], coords[2], coords[3]);
        }

      })
      var coords = null;
      if(g_type == 'circle'){
        var firstk =Math.hypot((e.offsetX -g_rect.startX),(e.offsetY -g_rect.startY))
        console.log(e);
        console.log(firstk);
        console.log(g_rect);
        drawCircle(firstk);
        coords = g_rect.startX+','+g_rect.startY+','+firstk;
      }
      else{
        mouse_canvas_draw();
        coords = g_rect.startX+','+g_rect.startY+','+g_rect.w+','+g_rect.h;

      }
      area = {'coords':coords,'shape':g_type}
      if(selected_idx != null){
        var href = $("#imageMapHref").val();
        var title = $("#imageMapTitle").val();
        var alt = $("#imageMapAlt").val();
        var target =  $("#mapTarget").val();
        area['href'] = href;
        area['title'] = title;
        area['alt'] = alt;
        area['target'] = target;
        selected.val(JSON.stringify(area));
      }

    }
  }, false);
}

function set_html() {
  target.removeAttribute('id');
  if (hasMap) { //create one
    map.parentNode.removeChild(map);
    //console.log(map);
    map = new CKEDITOR.dom.element('map');
    //console.log("Generating new image map name:",mapName);
    map.setAttribute('name', mapName); // set name
    map.setHtml(""); //truncate contents
  } else {
    target.setAttribute('usemap', "#" + mapName);
  }


  $.each($("#imageMapAreas option"),function(key,obj){
    var option_area =  $.parseJSON( $(obj).val());
    var coords = option_area.coords.split(",")
    if(option_area.shape == 'circle'){
      coords[0] = coords[0]/g_canvas_scale;
      coords[1] = coords[1]/g_canvas_scale;
      coords[2] = coords[2]/g_canvas_scale;
    }
    else{
      coords[0] = coords[0]/g_canvas_scale;
      coords[1] = coords[1]/g_canvas_scale;
      coords[2] = coords[2]/g_canvas_scale;
      coords[3] = coords[2]/g_canvas_scale;
    }

    var real_code = coords.join(',');
    var h=CKEDITOR.dom.element.createFromHtml("<area href='"+option_area.href+"' alt='"+option_area.alt+"' title='"+option_area.title+"' target='"+option_area.target+"' shape='"+option_area.shape+"' data-real='"+real_code+"' coords='"+option_area.coords+"'>");
    map.append(h);
    $(target.$).parent().append(map.getOuterHtml());
    editors.updateElement();
  })
  $('#map_modal').remove()
}

function mouse_canvas_draw() {
  ctx_damages.strokeRect(g_rect.startX, g_rect.startY, g_rect.w, g_rect.h);
}
function drawCircle(firstk){
  var k=firstk,siiry= g_rect.startY,siirx= g_rect.startX;
  ctx_damages.moveTo(0*k+siirx, 1*k+siiry);
  ctx_damages.bezierCurveTo(b*k+siirx, 1*k+siiry, 1*k+siirx, b*k+siiry, 1*k+siirx, 0+siiry);
  ctx_damages.moveTo(0*k-(2*0*k)+siirx, 1*k-(2*1*k)+siiry);
  ctx_damages.bezierCurveTo(b*k-(2*b*k)+siirx, 1*k-(2*1*k)+siiry, 1*k-(2*1*k)+siirx, b*k-(2*b*k)+siiry, 1*k-(2*1*k)+siirx, 0-(2*0)+siiry);
  ctx_damages.moveTo(0*k+siirx, 1*k+siiry);
  ctx_damages.bezierCurveTo(b*k-(2*b*k)+siirx, 1*k+siiry, 1*k-(2*1*k)+siirx, b*k+siiry, 1*k-(2*1*k)+siirx, 0-(2*0)+siiry);
  ctx_damages.moveTo(0*k-(2*0*k)+siirx, 1*k-(2*1*k)+siiry);
  ctx_damages.bezierCurveTo(b*k+siirx, 1*k-(2*1*k)+siiry, 1*k+siirx, b*k-(2*b*k)+siiry, 1*k+siirx, 0+siiry);
  ctx_damages.stroke();

}

function set_rect(){
  area = {};
  g_is_start = true;
  g_type = 'rect';
  $("#imageMapHref").val('');
  $("#imageMapTitle").val('');
  $("#imageMapAlt").val('');
  selected = null;
  selected_idx = null;
}

function set_circle(){
  area = {};
  g_is_start = true;
  g_type = 'circle';
  $("#imageMapHref").val('');
  $("#imageMapTitle").val('');
  $("#imageMapAlt").val('');
  selected = null;
  selected_idx = null;
}

function area_select(_this){
  selected = $("#imageMapAreas option:selected");
  selected_idx = $("#imageMapAreas option").index(selected);
  var option_area =  $.parseJSON( selected.val());
  area = {};
  g_is_start = true;
  g_type = option_area.shape;
  $("#imageMapHref").val(option_area.href);
  $("#imageMapTitle").val(option_area.title);
  $("#imageMapAlt").val(option_area.alt);
  $("#mapTarget option[value="+option_area.target+"]").prop('selected','selected');
}

function set_option()
{
  if(area.coords === undefined){
    alert('영역을 선택하십시오.');
    return false;
  }

  var href = $("#imageMapHref").val();
  if(href.length == 0){
    alert('링크는 필수입력입니다.');
    return false;
  }
  var title = $("#imageMapTitle").val();
  if(title.length == 0){
    alert('타이틀은 필수입력입니다.');
    return false;
  }

  selected = null;
  selected_idx = null;
  var alt = $("#imageMapAlt").val();
  var target =  $("#mapTarget").val();
  area['href'] = href;
  area['title'] = title;
  area['alt'] = alt;
  area['target'] = target;
  $("#imageMapHref").val('');
  $("#imageMapTitle").val('');
  $("#imageMapAlt").val('');
  $("#imageMapAreas").append(new Option(title,JSON.stringify(area)));

  area = {};
  g_is_start = false;
}

function del_option()
{
  selected = $("#imageMapAreas option:selected");
  if(selected.length == 0){
    alert('선택된 옵션이 없습니다.');
    return false;
  }
  selected_idx = $("#imageMapAreas option").index(selected);
  if(confirm('선택된 옵션을 삭제 하시겠습니까?')){
    selected.remove();
    selected = null;
    selected_idx = null;
    ctx_damages.clearRect(0,0,damages_canvas.width,damages_canvas.height);
    ctx_damages.beginPath();
    ctx_damages.strokeStyle =markerColor;
    ctx_damages.lineWidth = 1;
    $.each($("#imageMapAreas option"),function(key,obj){
      if(selected_idx != null &&  key == selected_idx){
        return
      }
      var option_area =  $.parseJSON( $(obj).val());
      var coords = option_area.coords.split(",")

      if(option_area.shape == 'circle'){
        ctx_damages.arc(coords[0], coords[1], coords[2], 0 ,Math.PI *2);
        ctx_damages.stroke(); //테두리
      }
      else{
        ctx_damages.strokeRect(coords[0], coords[1], coords[2], coords[3]);
      }

    })
  }
}
