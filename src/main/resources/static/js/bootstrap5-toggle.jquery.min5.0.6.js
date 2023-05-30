/* Copyright Notice
 * bootstrap5-toggle v5.0.6
 * https://palcarazm.github.io/bootstrap5-toggle/
 * @author 2011-2014 Min Hur (https://github.com/minhur)
 * @author 2018-2019 Brent Ely (https://github.com/gitbrent)
 * @author 2022 Pablo Alcaraz Martínez (https://github.com/palcarazm)
 * @funding GitHub Sponsors
 * @see https://github.com/sponsors/palcarazm
 * @license MIT
 * @see https://github.com/palcarazm/bootstrap5-toggle/blob/master/LICENSE
 */

!function(a){"use strict";function n(t,e){this.$element=a(t),this.options=a.extend({},this.defaults(),e),this.options.onlabel===n.DEPRECATION.value&&(this.$element.attr("data-on")?(n.DEPRECATION.log(n.DEPRECATION.ATTRIBUTE,"data-on","data-onlabel"),this.options.onlabel=this.$element.attr("data-on")):e.on?(n.DEPRECATION.log(n.DEPRECATION.OPTION,"on","onlabel"),this.options.onlabel=e.on):this.options.onlabel=n.DEFAULTS.onlabel),this.options.offlabel===n.DEPRECATION.value&&(this.$element.attr("data-off")?(n.DEPRECATION.log(n.DEPRECATION.ATTRIBUTE,"data-off","data-offlabel"),this.options.offlabel=this.$element.attr("data-off")):e.off?(n.DEPRECATION.log(n.DEPRECATION.OPTION,"off","offlabel"),this.options.offlabel=e.off):this.options.offlabel=n.DEFAULTS.offlabel),this.render()}function h(t,e){e.options.tristate?e.$toggle.hasClass("indeterminate")?(e.determinate(!0),e.toggle()):e.indeterminate():e.toggle(),t.preventDefault()}n.DEPRECATION={value:"BOOTSTRAP TOGGLE DEPRECATION CHECK -- a0Jhux0QySypjjs4tLtEo8xT2kx0AbYaq9K6mgNjWSs0HF0L8T8J0M0o3Kr7zkm7 --",ATTRIBUTE:"attribute",OPTION:"option",log:function(t,e,i){console.warn(`Bootstrap Toggle deprecation warning: Using ${e} ${t} is deprected. Use ${i} instead.`)}},n.DEFAULTS={onlabel:"On",offlabel:"Off",onstyle:"primary",offstyle:"secondary",onvalue:null,offvalue:null,ontitle:null,offtitle:null,size:"normal",style:"",width:null,height:null,tabindex:0,tristate:!1,name:null},n.prototype.defaults=function(){return{onlabel:this.$element.attr("data-onlabel")||n.DEPRECATION.value||n.DEFAULTS.onlabel,offlabel:this.$element.attr("data-offlabel")||n.DEPRECATION.value||n.DEFAULTS.offlabel,onstyle:this.$element.attr("data-onstyle")||n.DEFAULTS.onstyle,offstyle:this.$element.attr("data-offstyle")||n.DEFAULTS.offstyle,onvalue:this.$element.attr("value")||this.$element.attr("data-onvalue")||n.DEFAULTS.onvalue,offvalue:this.$element.attr("data-offvalue")||n.DEFAULTS.offvalue,ontitle:this.$element.attr("data-ontitle")||this.$element.attr("title")||n.DEFAULTS.ontitle,offtitle:this.$element.attr("data-offtitle")||this.$element.attr("title")||n.DEFAULTS.offtitle,size:this.$element.attr("data-size")||n.DEFAULTS.size,style:this.$element.attr("data-style")||n.DEFAULTS.style,width:this.$element.attr("data-width")||n.DEFAULTS.width,height:this.$element.attr("data-height")||n.DEFAULTS.height,tabindex:this.$element.attr("tabindex")||n.DEFAULTS.tabindex,tristate:this.$element.is("[tristate]")||n.DEFAULTS.tristate,name:this.$element.attr("name")||n.DEFAULTS.name}},n.prototype.render=function(){let t;switch(this.options.size){case"large":case"lg":t="btn-lg";break;case"small":case"sm":t="btn-sm";break;case"mini":case"xs":t="btn-xs";break;default:t=""}var e=a('<span class="btn">').html(this.options.onlabel).addClass("btn-"+this.options.onstyle+" "+t),i=(this.options.ontitle&&e.attr("title",this.options.ontitle),a('<span class="btn">').html(this.options.offlabel).addClass("btn-"+this.options.offstyle+" "+t)),o=(this.options.offtitle&&i.attr("title",this.options.offtitle),a('<span class="toggle-handle btn">').addClass(t)),s=a('<div class="toggle-group">').append(e,i,o),n=a('<div class="toggle btn" data-toggle="toggle" role="button">').addClass(this.$element.prop("checked")?"btn-"+this.options.onstyle:"btn-"+this.options.offstyle+" off").addClass(t).addClass(this.options.style).attr("tabindex",this.options.tabindex);(this.$element.prop("disabled")||this.$element.prop("readonly"))&&(n.addClass("disabled"),n.attr("disabled","disabled")),this.options.onvalue&&this.$element.val(this.options.onvalue);let l=null;this.options.offvalue&&((l=this.$element.clone()).val(this.options.offvalue),l.attr("data-toggle","invert-toggle"),l.removeAttr("id"),l.prop("checked",!this.$element.prop("checked"))),this.$element.wrap(n),a.extend(this,{$toggle:this.$element.parent(),$toggleOn:e,$toggleOff:i,$toggleGroup:s,$invElement:l}),this.$toggle.append(l,s),this.options.width?this.$toggle.css("width",this.options.width):(this.$toggle.css("min-width","100px"),this.$toggle.css("min-width",Math.max(e.outerWidth(),i.outerWidth())+o.outerWidth()/2+"px")),this.options.height?this.$toggle.css("height",this.options.height):(this.$toggle.css("min-height","36px"),this.$toggle.css("min-height",Math.max(e.outerHeight(),i.outerHeight())+"px")),e.addClass("toggle-on"),i.addClass("toggle-off"),this.options.height&&(e.css("line-height",e.height()+"px"),i.css("line-height",i.height()+"px")),this.$toggle.on("touchstart",t=>{h(t,this)}),this.$toggle.on("click",t=>{h(t,this)}),this.$toggle.on("keypress",t=>{" "==t.key&&h(t,this)}),this.$element.prop("id")&&a('label[for="'+this.$element.prop("id")+'"]').on("touchstart click",t=>{this.toggle(),this.$toggle.focus()})},n.prototype.toggle=function(t=!1){this.$element.prop("checked")?this.off(t):this.on(t)},n.prototype.on=function(t=!1){if(this.$element.prop("disabled")||this.$element.prop("readonly"))return!1;this.$toggle.removeClass("btn-"+this.options.offstyle+" off").addClass("btn-"+this.options.onstyle),this.$element.prop("checked",!0),this.$invElement&&this.$invElement.prop("checked",!1),t||this.trigger()},n.prototype.off=function(t=!1){if(this.$element.prop("disabled")||this.$element.prop("readonly"))return!1;this.$toggle.removeClass("btn-"+this.options.onstyle).addClass("btn-"+this.options.offstyle+" off"),this.$element.prop("checked",!1),this.$invElement&&this.$invElement.prop("checked",!0),t||this.trigger()},n.prototype.indeterminate=function(t=!1){if(!this.options.tristate||this.$element.prop("disabled")||this.$element.prop("readonly"))return!1;this.$toggle.addClass("indeterminate"),this.$element.prop("indeterminate",!0),this.$element.removeAttr("name"),this.$invElement&&this.$invElement.prop("indeterminate",!0),this.$invElement&&this.$invElement.removeAttr("name"),t||this.trigger()},n.prototype.determinate=function(t=!1){if(!this.options.tristate||this.$element.prop("disabled")||this.$element.prop("readonly"))return!1;this.$toggle.removeClass("indeterminate"),this.$element.prop("indeterminate",!1),this.options.name&&this.$element.attr("name",this.options.name),this.$invElement&&this.$invElement.prop("indeterminate",!1),this.$invElement&&this.options.name&&this.$invElement.attr("name",this.options.name),t||this.trigger()},n.prototype.enable=function(){this.$toggle.removeClass("disabled"),this.$toggle.removeAttr("disabled"),this.$element.prop("disabled",!1),this.$element.prop("readonly",!1),this.$invElement&&(this.$invElement.prop("disabled",!1),this.$invElement.prop("readonly",!1))},n.prototype.disable=function(){this.$toggle.addClass("disabled"),this.$toggle.attr("disabled","disabled"),this.$element.prop("disabled",!0),this.$element.prop("readonly",!1),this.$invElement&&(this.$invElement.prop("disabled",!0),this.$invElement.prop("readonly",!1))},n.prototype.readonly=function(){this.$toggle.addClass("disabled"),this.$toggle.attr("disabled","disabled"),this.$element.prop("disabled",!1),this.$element.prop("readonly",!0),this.$invElement&&(this.$invElement.prop("disabled",!1),this.$invElement.prop("readonly",!0))},n.prototype.update=function(t){this.$element.prop("disabled")?this.disable():this.$element.prop("readonly")?this.readonly():this.enable(),this.$element.prop("checked")?this.on(t):this.off(t)},n.prototype.trigger=function(t){this.$element.off("change.bs.toggle"),t||this.$element.change(),this.$element.on("change.bs.toggle",a.proxy(function(){this.update()},this))},n.prototype.destroy=function(){this.$element.off("change.bs.toggle"),this.$toggleGroup.remove(),this.$invElement&&this.$invElement.remove(),this.$element.removeData("bs.toggle"),this.$element.unwrap()};let t=a.fn.bootstrapToggle;a.fn.bootstrapToggle=function(o){let s=Array.prototype.slice.call(arguments,1)[0];return this.each(function(){var t=a(this);let e=t.data("bs.toggle");var i="object"==typeof o&&o;e||(e=new n(this,i),t.data("bs.toggle",e)),"string"==typeof o&&e[o]&&"boolean"==typeof s?e[o](s):"string"==typeof o&&e[o]&&e[o]()})},a.fn.bootstrapToggle.Constructor=n,a.fn.toggle.noConflict=function(){return a.fn.bootstrapToggle=t,this},a(function(){a("input[type=checkbox][data-toggle^=toggle]").bootstrapToggle()})}(jQuery);
//# sourceMappingURL=bootstrap5-toggle.jquery.min.js.map