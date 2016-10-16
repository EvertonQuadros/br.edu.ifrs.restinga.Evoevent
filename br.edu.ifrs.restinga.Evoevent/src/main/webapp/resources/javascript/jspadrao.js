/* 
 * Copyright 2016 notrevequadrosc@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Author:  notrevequadrosc@gmail.com
 * Created: 09/10/2016
 */

function handleResizeDialog(dialog) {
    
    var el = $(dialog.jqId);
    var doc = $('body');
    var elPos = '';
    var bodyHeight = '';
    var bodyWidth = '';

    if (el.height() > doc.height()) {
        
       bodyHeight = el.height() + 'px';
       elPos = 'absolute';
       
    }   

    if (el.width() > doc.width()) {
        
       bodyWidth = el.width() + 'px';
       elPos = 'absolute';
       
    }

    el.css('position', elPos);
    doc.css('width', bodyWidth);
    doc.css('height', bodyHeight);

    var pos = el.offset();

    if (pos.top + el.height() > doc.height()){
       pos.top = doc.height() - el.height();
    }

    if (pos.left + el.width() > doc.width()){
       pos.left = doc.width() - el.width();
    }

    var offsetX = 0;
    var offsetY = 0;

    if (elPos != 'absolute') {
        
       offsetX = $(window).scrollLeft();
       offsetY = $(window).scrollTop();
       
    }

    // scroll fix for position fixed
    if (pos.left < offsetX){
       pos.left = offsetX;
    }
    if (pos.top < offsetY){
       pos.top = offsetY;
    }

    el.offset(pos);

}
