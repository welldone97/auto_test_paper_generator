/*
    自定义工具
    I am liangzhenyu
    2018-07-05
*/

/*
插件-弹框提示-v1.3.5(可以展示大内容，也可以是确认框)
使用方法说明：
    1.此插件基于jQuery编写，使用时需要先导入jQuery
    2.获取对象
        var myPop = $.initPopop(cfg);
        cfg:配置文件如果不设置将使用默认设置
    3.设置配置
        myPop.setStyle(cfg, status);
        cfg:配置文件如果不设置将使用默认设置
        status:配置文件的状态（false:在原配置上追加，重复的将覆盖；true:全新覆盖，未配置的将使用默认值），默认值为false
    4.显示提示框
        myPop.showPopup(content,title);
        content:任意内容（可选），若无则使用 cfg 配置中的内容设置，若cfg配置中也没有设置将为空白，强制水平和垂直居中
        title:标题(可选)，若无则使用 cfg 配置中的标题设置，若cfg配置中也没有设置将使用默认值：“消息”
    5.关闭提示框
        myPop.closePopup();
    6.添加框低按钮
        myPop.addButton(btnName,callback); //需要注意的是添加的按键过多（弹框的宽不足容纳所有按键）时，将自动适应设置弹框的宽来容纳所有按键
        btnName:按键的名称,注：添加的名称含有“关闭，取消”等的将带有点击关闭弹框的事件，
        callback:回调函数(按键点击事件在这里，就是说每添加一个按键都自动绑定了一个点击事件)，函数中的 this->当前弹框对象
    7.移除框低按钮
        myPop.removeButton(nameOrIndex);
        nameOrIndex:按键的名称或者按键所在的下标
    8.获取本弹框状态
        myPop.getStatus();
        返回值:{
            status: true/false，弹框的状态，true为开启状态，false为关闭状态，
            rect:{top,left,bottom,right,width,height}，弹框绝对位置相关值,
            version:版本
        }
    9.弹框开启关闭回调事件
        myPop.on(type,callback);
        type:"open"开启，"close"关闭两种类型，按照jquery的on方法使用习惯配置便可
        callback:回调函数
 参数说明：
    cfg:{
        width:弹框的长,
        height:弹框的高,
        border:弹框边框设置,
        borderRadius:边框的圆角大小,
        themeColor:弹框的主题颜色,
        themeStyle:主题风格，目前有两种："default"和"dimnav",默认值:"default",
        animation:弹框弹出风格动画，1、弹出；2、收缩；3、旋转；4、下滑；5、上滑；6、抖动
        isShowShadow:是否显示边框阴影,默认为true,
        shadowSize:阴影的长度，如果 isShowShadow为false则该值无效，默认长度为12px，
        isShowIcon:是否显示左上图标,默认:true,
        icon:图标的图片（可以是路径；也可以是图片base64编码；也可以直接是字符，比如：'!','?'）,
        allowedFullscreen:是否允许全屏，即是否添加全屏按键，默认值：false,
        allowedKeyboard:是否允许键盘操作，目前暂时只要Esc按键点击退出事件，默认允许，true
        content:任意内容,可以是节点，可以是ID，可以是类。使用建议：弹框作为模态框时建议在这里配置选择器，作为提示框时这里不要配置，直接在showPopup方法配置；
            简而言之就是不常改变的内容（一般都是配置了样式的节点）放在这，经常改变的内容（一般都是一句疑问句）就放在showPopup方法中。
        title:弹框标题,
        buttonAlign:按键对齐方式，left,center和right三种方式,
        isShowBg:是否显示背景,默认：false,
        isClickBgClose:是否点击背景关闭弹框，默认：true，
        allowedMove:是否允许移动,默认：false,
        targetSelector:参照目标，用于弹框设置位置的相对节点，默认：'window'，相对于屏幕设置位置（这样能保证绝对定位），强调：这个'window'不是选择器，这个只是标识绝对定位的意思；
            其他比如：body，div，#myId，.myClass等节点或选择器，这些是用于设置相对位置（必须是真实的节点或选择器）
        insideOrFollow:弹框类型设置，“inside”和“follow”两种类型(内嵌和跟随)，默认“inside”，内嵌或跟随(targetSelector)显示,
        relativePosition:弹框相对目标选择器的位置，
            当'insideOrFollow' 值为“inside”时默认中间：center，可选值：top，bottom,left，right及组合方位词，也可以是"x,y"的绝对定位；
            当'insideOrFollow' 值为“follow”时默认右下：right bottom，可选值：top，bottom,left，right及组合方位词
        relativeOffset: 弹框与目标相对偏差位置（就是弹框和targetSelector的距离有多远），默认偏移10px
    }
    注：参数配置里没有 z-index 配置项，如果需要配置请自行直接修改css文件 .lzy_custom_bg 和 .lzy_custom_popup 两个类


  另：作者我没有按照 打开弹框时便插入节点，关闭弹框时移除节点 的流程。是因为弹框的按钮以及弹框内容部分可能开发者需要对里面的控件添加事件，如果移除节点将导致事件失效。
    但为了保持美观，编写一个弹框使用一个大 DIV 包括
 */
;(function ($, window, document, undefined) {
    $.extend({
        initPopup: function (cfg) {
            /**
             * 将颜色值转为 rgb 类型的值（不支持rgba）
             * @param color 颜色值，rgb,16进制值，英文名称
             * @returns {*} rgb值
             */
            var toRGB = function (color) {
                color = color.toLowerCase();
                var _EN_COLOR = '{"aliceblue":"#f0f8ff","antiquewhite":"#faebd7","aqua":"#00ffff","aquamarine":"#7fffd4","azure":"#f0ffff","beige":"#f5f5dc","bisque":"#ffe4c4","black":"#000000","blanchedalmond":"#ffebcd","blue":"#0000ff","blueviolet":"#8a2be2","brown":"#a52a2a","burlywood":"#deb887","cadetblue":"#5f9ea0","chartreuse":"#7fff00","chocolate":"#d2691e","coral":"#ff7f50","cornflowerblue":"#6495ed","cornsilk":"#fff8dc","crimson":"#dc143c","cyan":"#00ffff","darkblue":"#00008b","darkcyan":"#008b8b","darkgoldenrod":"#b886b","darkgray":"#a9a9a9","darkgreen":"#006400","darkkhaki":"#bdb76b","darkmagenta":"#8b008b","darkolivegreen":"#556b2f","darkorange":"#ff8c00","darkorchid":"#9932cc","darkred":"#8b0000","darksalmon":"#e9967a","darkseagreen":"#8fbc8f","darkslateblue":"#483d8b","darkslategray":"#2f4f4f","darkturquoise":"#00ced1","darkviolet":"#9400d3","deeppink":"#ff1493","deepskyblue":"#00bfff","dimgray":"#696969","dodgerblue":"#1e90ff","feldspar":"#d19275","firebrick":"#b22222","floralwhite":"#fffaf0","forestgreen":"#228b22","fuchsia":"#ff00ff","gainsboro":"#dcdcdc","ghostwhite":"#f8f8ff","gold":"#ffd700","goldenrod":"#daa520","gray":"#808080","green":"#008000","greenyellow":"#adff2f","honeydew":"#f0fff0","hotpink":"#ff69b4","indianred":"#cd5c5c","indigo":"#4b0082","ivory":"#fffff0","khaki":"#f0e68c","lavender":"#e6e6fa","lavenderblush":"#fff0f5","lawngreen":"#7cfc00","lemonchiffon":"#fffacd","lightblue":"#add8e6","lightcoral":"#f08080","lightcyan":"#e0ffff","lightgoldenrodyellow":"#fafad2","lightgrey":"#d3d3d3","lightgreen":"#90ee90","lightpink":"#ffb6c1","lightsalmon":"#ffa07a","lightseagreen":"#20b2aa","lightskyblue":"#87cefa","lightslateblue":"#8470ff","lightslategray":"#778899","lightsteelblue":"#b0c4de","lightyellow":"#ffffe0","lime":"#00ff00","limegreen":"#32cd32","linen":"#faf0e6","magenta":"#ff00ff","maroon":"#800000","mediumaquamarine":"#66cdaa","mediumblue":"#0000cd","mediumorchid":"#ba55d3","mediumpurple":"#9370d8","mediumseagreen":"#3cb371","mediumslateblue":"#7b68ee","mediumspringgreen":"#00fa9a","mediumturquoise":"#48d1cc","mediumvioletred":"#c71585","midnightblue":"#191970","mintcream":"#f5fffa","mistyrose":"#ffe4e1","moccasin":"#ffe4b5","navajowhite":"#ffdead","navy":"#000080","oldlace":"#fdf5e6","olive":"#808000","olivedrab":"#6b8e23","orange":"#ffa500","orangered":"#ff4500","orchid":"#da70d6","palegoldenrod":"#eee8aa","palegreen":"#98fb98","paleturquoise":"#afeeee","palevioletred":"#d87093","papayawhip":"#ffefd5","peachpuff":"#ffdab9","peru":"#cd853f","pink":"#ffc0cb","plum":"#dda0dd","powderblue":"#b0e0e6","purple":"#800080","red":"#ff0000","rosybrown":"#bc8f8f","royalblue":"#4169e1","saddlebrown":"#8b4513","salmon":"#fa8072","sandybrown":"#f4a460","seagreen":"#2e8b57","seashell":"#fff5ee","sienna":"#a0522d","silver":"#c0c0c0","skyblue":"#87ceeb","slateblue":"#6a5acd","slategray":"#708090","snow":"#fffafa","springgreen":"#00ff7f","steelblue":"#4682b4","tan":"#d2b48c","teal":"#008080","thistle":"#d8bfd8","tomato":"#ff6347","turquoise":"#40e0d0","violet":"#ee82ee","violetred":"#d02090","wheat":"#f5deb3","white":"#ffffff","whitesmoke":"#f5f5f5","yellow":"#ffff00","yellowgreen":"#9acd32"}';
                var _Hex2RGB = function (color) {
                    var i = 0, arrColor = [];
                    if (color.length === 4) {
                        var nColor = "#";
                        for (i = 1; i < 4; i += 1) nColor += color.slice(i, i + 1).concat(color.slice(i, i + 1));
                        color = nColor;
                    }
                    for (i = 1; i < 7; i += 2) arrColor.push(parseInt("0x" + color.slice(i, i + 2)));
                    return "rgb(" + arrColor[0] + "," + arrColor[1] + "," + arrColor[2] + ")";
                };
                if (color.indexOf("rgba") >= 0) {
                    return color;
                } else if (color && /^#([0-9a-f]{3}|[0-9a-f]{6})$/.test(color)) {
                    return _Hex2RGB(color);
                } else if (color.indexOf("rgb") >= 0) {
                    if (color.indexOf("%") >= 0) {
                        var arrRgb = color.replace("rgb(", "").replace(")", "").replace(/%/g, "").split(",");
                        return "rgb(" + parseInt(arrRgb[0] / 100 * 255) + "," + parseInt(arrRgb[1] / 100 * 255) + "," + parseInt(arrRgb[2] / 100 * 255) + ")";
                    }
                    return color;
                } else {
                    var hexColor = JSON.parse(_EN_COLOR)[color];
                    if (!hexColor) {
                        console.error("抱歉！无该英文颜色");
                        return color;
                    }
                    return _Hex2RGB(hexColor);
                }
            };
            /**
             * 转化为淡颜色
             * @param color rgba值或rgb值
             * @param opacity 透明度
             * @returns {string} rgba值
             */
            var dimColor = function (color, opacity) {
                var opa = opacity || 0.5;
                if (color.indexOf("rgba") >= 0) return color.substr(0, color.lastIndexOf(",")) + "," + parseFloat(color.substr(color.lastIndexOf(",") + 1, color.lastIndexOf(")") - 1)) * opa + ")";
                var arrRgb = toRGB(color).replace("rgb(", "").replace(")", "").split(",");
                return "rgba(" + arrRgb[0] + "," + arrRgb[1] + "," + arrRgb[2] + "," + opa + ")";
            };
            /**
             * 转化为暗颜色
             * @param color rgb值
             * @param oft
             * @returns {string}
             */
            var darkColor = function (color, oft) {
                oft = oft || 0.5;
                var arrRgb = toRGB(color).replace("rgb(", "").replace(")", "").split(",");
                return "rgb(" + parseInt(arrRgb[0] * oft) + "," + parseInt(arrRgb[1] * oft) + "," + parseInt(arrRgb[2] * oft) + ")";
            };
            /**
             * 判断是否是IE家族的浏览器
             * @returns {*}
             */
            var isIE = function () {
                var userAgent = navigator.userAgent;
                var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器
                var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器
                var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
                if (isIE) {
                    new RegExp("MSIE (\\d+\\.\\d+);").test(userAgent);
                    var ieV = parseInt(RegExp["$1"]);
                    if (ieV === 7) return 7;
                    else if (ieV === 8) return 8;
                    else if (ieV === 9) return 9;
                    else if (ieV === 10) return 10;
                    else return 1;
                }
                if (isEdge) return "edge";
                if (isIE11) return 11;
                return false;
            };
            /**
             * 判断是否是火狐浏览器
             * @returns {boolean}
             */
            var isFire = function () {
                return navigator.userAgent.indexOf("Firefox") > -1;
            };
            /**
             * 用来给弹框判断是否使用屏幕作参照对象
             * @param ele 节点
             * @returns {boolean}
             */
            var isReferScreen = function (ele) {
                return !ele || ele === "window" || ele === "document" || ele === "screen" || ele === window || ele === document;
            };
            /**
             * 获取body下的所有子节点中最大的zIndex值
             */
            var getMaxZIndex = function () {
                var arr = [], $objs = $("body>*");
                for (var i = 0; i < $objs.length; i++) {
                    var z = $($objs[i]).css("zIndex");
                    arr.push(isNaN(z) ? 0 : z);
                }
                var maxZIndex = arr.length ? Math.max.apply(null, arr) : 0;
                return maxZIndex > 999999 ? maxZIndex + 1 : 1000000;
            };
            /**
             * 弹框的主体入口方法
             * @param cfg 配置
             */
            var lzyPopup = function (cfg) {
                this.version = "v1.3.5";
                this.cfg = null;
                this.fullTemp = {full: false};
                this.openEvents = [];
                this.closeEvents = [];
                this.$bgObj = $("<div class='lzy_custom_bg'></div>");
                this.$popupObj = $("<div class='lzy_custom_popup'><div class='lzy_popup_nav'><div class='lzy_nav_icon'>!</div><div class='lzy_nav_title'>消息</div><svg class='lzy_nav_close'><rect width='18' height='18'/><line x1='4' y1='4' x2='14' y2='14'/><line x1='4' y1='14' x2='14' y2='4'/></svg><div class='lzy_nav_full'></div></div><div class='lzy_popup_cont'></div><div class='lzy_popup_footer'></div></div>");
                this.zoominBtn = "<svg><rect x='4' y='4' width='10' height='10' style='stroke: %color' /></svg>";
                this.zoomoutBtn = "<svg><rect x='4' y='6' width='8' height='8' style='stroke: %color'/><line x1='5' y1='4' x2='14' y2='4' style='stroke: %color'/><line x1='14' y1='3' x2='14' y2='13' style='stroke: %color'/></svg>";
                this.zoomBtnColor = "";
                this.setStyle(cfg, true);
            };
            lzyPopup.prototype = {
                setStyle: function (cfg, status) {
                    cfg = cfg || {};
                    var isEmpty = function (val) {
                        //该方法的作用主要是避免传入参数为 0  时的判断却为false
                        return val === null || typeof(val) === "undefined";
                    };
                    if (status) {
                        //全新插入参数，未配置的使用默认参
                        this.cfg = {
                            width: cfg.width || 350,
                            height: cfg.height || 200,
                            border: cfg.border || '1px solid black',
                            borderRadius: isEmpty(cfg.borderRadius) ? 5 : cfg.borderRadius,
                            themeColor: cfg.themeColor || "#358aff",
                            themeStyle: (cfg.themeStyle || 'default').toLowerCase(),
                            animation: cfg.animation || 1,
                            isShowIcon: isEmpty(cfg.isShowIcon) ? true : cfg.isShowIcon,
                            icon: cfg.icon || null,
                            allowedFullscreen: cfg.allowedFullscreen,
                            allowedKeyboard: isEmpty(cfg.allowedKeyboard) ? true : cfg.allowedKeyboard,
                            content: cfg.content || null,
                            title: cfg.title || null,
                            buttonAlign: cfg.buttonAlign || 'right',
                            isShowShadow: isEmpty(cfg.isShowShadow) ? true : cfg.isShowShadow,
                            shadowSize: isEmpty(cfg.shadowSize) ? 12 : cfg.shadowSize,
                            isShowBg: cfg.isShowBg,
                            isClickBgClose: isEmpty(cfg.isClickBgClose) ? true : cfg.isClickBgClose,
                            allowedMove: cfg.allowedMove,
                            targetSelector: cfg.targetSelector || 'window',
                            insideOrFollow: (cfg.insideOrFollow || 'inside').toLowerCase(),
                            relativePosition: (cfg.relativePosition || 'center').toLowerCase(),
                            relativeOffset: isEmpty(cfg.relativeOffset) ? 10 : cfg.relativeOffset
                        };
                        if (!cfg.relativePosition && this.cfg.insideOrFollow === 'follow') this.cfg.relativePosition = "right bottom";
                    } else {
                        //追加插入参数，未配置的使用默认参
                        this.cfg = {
                            width: cfg.width || this.cfg.width,
                            height: cfg.height || this.cfg.height,
                            border: cfg.border || this.cfg.border,
                            borderRadius: !isEmpty(cfg.borderRadius) ? cfg.borderRadius : this.cfg.borderRadius,
                            themeColor: cfg.themeColor || this.cfg.themeColor,
                            themeStyle: (cfg.themeStyle || this.cfg.themeStyle).toLowerCase(),
                            animation: cfg.animation || this.cfg.animation,
                            isShowIcon: !isEmpty(cfg.isShowIcon) ? cfg.isShowIcon : this.cfg.isShowIcon,
                            icon: cfg.icon || this.cfg.icon,
                            allowedFullscreen: !isEmpty(cfg.allowedFullscreen) ? cfg.allowedFullscreen : this.cfg.allowedFullscreen,
                            allowedKeyboard: !isEmpty(cfg.allowedKeyboard) ? cfg.allowedKeyboard : this.cfg.allowedKeyboard,
                            content: cfg.content || this.cfg.content,
                            title: cfg.title || this.cfg.title,
                            buttonAlign: cfg.buttonAlign || this.cfg.buttonAlign,
                            isShowShadow: !isEmpty(cfg.isShowShadow) ? cfg.isShowShadow : this.cfg.isShowShadow,
                            shadowSize: !isEmpty(cfg.shadowSize) ? cfg.shadowSize : this.cfg.shadowSize,
                            isShowBg: !isEmpty(cfg.isShowBg) ? cfg.isShowBg : this.cfg.isShowBg,
                            isClickBgClose: !isEmpty(cfg.isClickBgClose) ? cfg.isClickBgClose : this.cfg.isClickBgClose,
                            allowedMove: !isEmpty(cfg.allowedMove) ? cfg.allowedMove : this.cfg.allowedMove,
                            targetSelector: cfg.targetSelector || this.cfg.targetSelector,
                            insideOrFollow: (cfg.insideOrFollow || this.cfg.insideOrFollow).toLowerCase(),
                            relativePosition: (cfg.relativePosition || this.cfg.relativePosition).toLowerCase(),
                            relativeOffset: !isEmpty(cfg.relativeOffset) ? cfg.relativeOffset : this.cfg.relativeOffset
                        };
                    }
                    //配置样式
                    var that = this;
                    this.$popupObj.off("click").on("click", function (e) {
                        e.stopPropagation();
                        if (that.cfg.isShowBg) that.$bgObj.css("z-index", getMaxZIndex());
                        else that.$popupObj.css("z-index", getMaxZIndex())
                    });
                    this.$popupObj.css({
                        'width': this.cfg.width,
                        'height': this.cfg.height,
                        'border': this.cfg.border,
                        'border-radius': this.cfg.borderRadius
                    }).find(".lzy_popup_footer").css({
                        'text-align': cfg.buttonAlign
                    });
                    this.$popupObj.css({'border-color': this.cfg.themeColor});
                    this.$popupObj.find(".lzy_popup_nav").css({'border-bottom-color': this.cfg.themeColor});

                    if (this.cfg.themeStyle === "default") {
                        this.$popupObj.find(".lzy_popup_nav").css({'background-color': this.cfg.themeColor});
                        this.$popupObj.find(".lzy_nav_icon").css({'border-color': '#fff', 'color': '#fff'});
                        this.$popupObj.find(".lzy_nav_title").css({'color': '#fff'});
                        this.$popupObj.find(".lzy_nav_close").find("line").css({'stroke': '#fff'});
                        this.zoomBtnColor = '#fff';
                    }
                    if (this.cfg.themeStyle === "dimnav") {
                        this.$popupObj.find(".lzy_popup_nav").css({'background-color': dimColor(this.cfg.themeColor, .15)});
                        this.$popupObj.find(".lzy_nav_icon").css({
                            'border-color': this.cfg.themeColor,
                            'color': this.cfg.themeColor
                        });
                        this.$popupObj.find(".lzy_nav_title").css({'color': '#000'});
                        this.$popupObj.find(".lzy_nav_close").find("line").css({'stroke': this.cfg.themeColor});
                        this.zoomBtnColor = this.cfg.themeColor;
                    }
                    this.$popupObj.find(".lzy_nav_close").off("mouseenter mouseleave click").hover(function () {
                        $(this).find("rect").css("fill", that.cfg.themeStyle === "default" ? darkColor(that.cfg.themeColor, .85) : dimColor(that.cfg.themeColor, .25));
                    }, function () {
                        $(this).find("rect").css("fill", "rgba(0,0,0,0)");
                    }).on("click", function () {
                        that.closePopup();
                    });

                    this.$bgObj.css('background-color', dimColor(this.cfg.themeColor, .2));
                    //背景是否显示
                    if (this.cfg.isShowBg) {
                        this.$bgObj.appendTo("body").append(this.$popupObj);
                        if (this.cfg.isClickBgClose) {
                            this.$bgObj.off("click").on("click", function () {
                                that.closePopup();
                            });
                        }
                    } else this.$popupObj.appendTo("body");

                    //设置边框阴影
                    this.$popupObj.css({'box-shadow': "0 0 " + (this.cfg.isShowShadow ? this.cfg.shadowSize.toString().replace("px", "") + "px " + this.cfg.themeColor : "0")});

                    //是否显示图标
                    if (this.cfg.isShowIcon) {
                        if (this.cfg.icon) {
                            //更换图标
                            if (this.cfg.icon && this.cfg.icon.indexOf("/") >= 0) {
                                this.$popupObj.find(".lzy_nav_icon").css({
                                    'background-image': 'url(' + this.cfg.icon + ')',
                                    'border-radius': 0,
                                    'border': 'none'
                                }).html("");
                            } else {
                                this.$popupObj.find(".lzy_nav_icon").html(this.cfg.icon);
                            }
                        }
                    } else {
                        this.$popupObj.find(".lzy_nav_icon").hide();
                    }
                    //是否允许拖动
                    if (this.cfg.allowedMove) {
                        //拖动事件
                        that.$popupObj.find(".lzy_popup_nav").addClass("lzy_popup_nav_move").off("mousedown").mousedown(function (e) {
                            var isMove = true;
                            var div_x = e.pageX - that.$popupObj.offset().left;
                            var div_y = e.pageY - that.$popupObj.offset().top;
                            $(document).off("mousemove mouseup").mousemove(function (e) {
                                if (isMove) that.$popupObj.css({'left': e.pageX - div_x, 'top': e.pageY - div_y});
                            }).mouseup(function () {
                                isMove = false;
                            });
                        });
                    }
                    //是否允许放大全屏
                    if (this.cfg.allowedFullscreen) {
                        this.$popupObj.find(".lzy_nav_full").show()
                            .append($(this.zoominBtn.replace(/%color/g, this.zoomBtnColor)))
                            .off("mouseenter mouseleave click")
                            .hover(function () {
                                $(this).css("background-color", that.cfg.themeStyle === "default" ? darkColor(that.cfg.themeColor, .85) : dimColor(that.cfg.themeColor, .25));
                            }, function () {
                                $(this).css("background-color", "rgba(0,0,0,0)");
                            })
                            .on("click", function () {
                                $(this).css("background-color", "rgba(0,0,0,0)");
                                if ($(this).find("line").length === 0) {
                                    $(this).html($(that.zoomoutBtn.replace(/%color/g, that.zoomBtnColor)));
                                    that.fullTemp = {
                                        top: that.$popupObj.css("top"),
                                        left: that.$popupObj.css("left"),
                                        full: true
                                    };
                                    //放大动画
                                    that.$popupObj.animate({
                                        'top': 1,
                                        'left': 1,
                                        'width': window.innerWidth - that.$popupObj.css("border-left-width").replace("px", "") * 2 - 2,
                                        'height': window.innerHeight - that.$popupObj.css("border-left-width").replace("px", "") * 2 - 2
                                    }, "fast");
                                } else {
                                    $(this).html($(that.zoominBtn.replace(/%color/g, that.zoomBtnColor)));
                                    that.fullTemp.full = false;
                                    //缩回动画
                                    that.$popupObj.animate({
                                        'top': that.fullTemp.top,
                                        'left': that.fullTemp.left,
                                        'width': that.cfg.width,
                                        'height': that.cfg.height
                                    }, "fast");
                                }
                            });
                    }
                    //是否允许键盘事件
                    if (this.cfg.allowedKeyboard) {
                        $(document).on("keyup", function (event) {
                            var e = event || window.event || arguments.callee.caller.arguments[0];
                            if (e && e.keyCode === 27) that.closePopup();
                        });
                    }
                    //配置标题
                    if (this.cfg.title) this.$popupObj.find(".lzy_nav_title").html(this.cfg.title);
                    //配置内容
                    if (this.cfg.content) {
                        if ($(this.cfg.content).get(0)) this.$popupObj.find(".lzy_popup_cont").append($(this.cfg.content));
                        else this.$popupObj.find(".lzy_popup_cont").html("<span>" + this.cfg.content + "</span>");
                    }

                    if (isIE()) this.$popupObj.find(".lzy_nav_title").css('line-height', "33px");//适配IE浏览器样式
                    return this;
                },
                showPopup: function (content, title) {
                    try {
                        for (var i = 0; i < this.openEvents.length; i++) this.openEvents[i].call(this);
                    } catch (e) {
                        console.error(e);
                    }
                    if (this.fullTemp.full) {
                        //全屏状态设置
                        this.$popupObj.css({
                            'top': 1,
                            'left': 1,
                            "margin-top": 0,
                            "margin-left": 0,
                            'width': window.innerWidth - this.$popupObj.css("border-left-width").replace("px", "") * 2 - 2,
                            'height': window.innerHeight - this.$popupObj.css("border-left-width").replace("px", "") * 2 - 2
                        });
                    } else {
                        //获取目标选择器的绝对位置
                        var rect, rPos = this.cfg.relativePosition, that = this;
                        var halfW = this.cfg.width / 2, halfH = this.cfg.height / 2, oft = this.cfg.relativeOffset;
                        //设置位置
                        var setCss = function (val1, val2) {
                            that.$popupObj.css({
                                'left': parseFloat(val1) - that.cfg.width / 2,
                                'top': parseFloat(val2) - that.cfg.height / 2
                            });
                        };
                        //判断位置配置参数
                        var checkPos = function () {
                            var size = 0;
                            for (var i = 1; i < arguments.length; i++) {
                                if (arguments[0].indexOf(arguments[i]) >= 0) size++;
                            }
                            return size === arguments.length - 1;
                        };
                        if (this.cfg.insideOrFollow === 'follow') {
                            //跟随状态
                            if (isReferScreen(this.cfg.targetSelector)) {
                                console.error("无法获取参照节点(targetSelector)的位置，请检查是否使用屏幕作参照对象(targetSelector='window')! \n");
                                return false;
                            }
                            rect = $(this.cfg.targetSelector).get(0).getBoundingClientRect();
                            // this.$popupObj.css({marginTop: 0, marginRight: 0, marginBottom: 0, marginLeft: 0});
                            if (checkPos(rPos, "top")) setCss(rect.left + halfW, rect.top - halfH - oft);
                            if (checkPos(rPos, "bottom")) setCss(rect.left + halfW, rect.bottom + halfH + oft);
                            if (checkPos(rPos, "left")) setCss(rect.left - halfW - oft, rect.top + halfH);
                            if (checkPos(rPos, "right")) setCss(rect.right + halfW + oft, rect.top + halfH);
                            if (checkPos(rPos, "top", "right")) setCss(rect.right + halfW + oft, rect.top - halfH - oft);
                            if (checkPos(rPos, "top", "left")) setCss(rect.left - halfW - oft, rect.top - halfH - oft);
                            if (checkPos(rPos, "bottom", "left")) setCss(rect.left - halfW - oft, rect.bottom + halfH + oft);
                            if (checkPos(rPos, "bottom", "right")) setCss(rect.right + halfW + oft, rect.bottom + halfH + oft);
                        } else {
                            //内嵌状态
                            if ("toprightbottomleftcenter".indexOf(rPos.replace(/\s+/g, "").substr(0, 2)) >= 0) {
                                if (isReferScreen(this.cfg.targetSelector)) {
                                    //绝对定位情况
                                    rect = {
                                        width: window.innerWidth,
                                        height: window.innerHeight,
                                        top: 0,
                                        right: 0,
                                        bottom: 0,
                                        left: 0
                                    };
                                } else rect = $(this.cfg.targetSelector).get(0).getBoundingClientRect(); //相对定位情况
                                if (checkPos(rPos, "top")) setCss(rect.left + rect.width / 2, rect.top + halfH + oft);
                                if (checkPos(rPos, "bottom")) setCss(rect.left + rect.width / 2, rect.top + rect.height - halfH - oft);
                                if (checkPos(rPos, "left")) setCss(rect.left + halfW + oft, rect.top + rect.height / 2);
                                if (checkPos(rPos, "right")) setCss(rect.left + rect.width - halfW - oft, rect.top + rect.height / 2);
                                if (checkPos(rPos, "top", "right")) setCss(rect.left + rect.width - halfW - oft, rect.top + halfH + oft);
                                if (checkPos(rPos, "top", "left")) setCss(rect.left + halfW + oft, rect.top + halfH + oft);
                                if (checkPos(rPos, "bottom", "left")) setCss(rect.left + halfW + oft, rect.top + rect.height - halfH - oft);
                                if (checkPos(rPos, "bottom", "right")) setCss(rect.left + rect.width - halfW - oft, rect.top + rect.height - halfH - oft);
                                if (checkPos(rPos, "center")) setCss(rect.left + rect.width / 2, rect.top + rect.height / 2);
                            } else {
                                var arrpos = rPos.replace(/px/g, "").replace(/\s+/g, "").split(",");
                                if (arrpos.length === 1) setCss(parseFloat(arrpos[0]) + halfW, halfH);
                                else setCss(parseFloat(arrpos[0]) + halfW, parseFloat(arrpos[1]) + halfH);
                            }
                        }
                        if (this.cfg.isShowBg) {
                            this.$bgObj.css({
                                width: rect.width,
                                height: rect.height,
                                top: rect.top,
                                left: rect.left
                            })
                        }
                    }

                    //配置各项底部按键等的样式(因为添加按键是不定性的，后滞的，无法预先在初始化时配置)
                    if (this.$popupObj.find(".lzy_popup_footer").find(".lzy_footer_btn").length === 0) {
                        this.$popupObj.find(".lzy_popup_cont").css('height', 'calc(100% - 30px)').next().css('height', '0');
                    }
                    this.$popupObj.find(".lzy_popup_footer").css("text-align", this.cfg.buttonAlign);
                    this.$popupObj.find(".lzy_footer_btn").css({
                        'border-color': this.cfg.themeColor,
                        'background-color': this.cfg.themeColor
                    });
                    if (isFire()) this.$popupObj.find(".lzy_footer_btn").css({'height': '23px'});
                    if (isIE()) this.$popupObj.find(".lzy_footer_btn").css({'line-height': '24px'});

                    this.$popupObj.find(".lzy_footer_btn_close").css({
                        'color': this.cfg.themeColor,
                        'border-color': this.cfg.themeColor,
                        'background-color': 'white'
                    });
                    //配置标题
                    if (title) this.$popupObj.find(".lzy_nav_title").html(title);
                    //配置内容
                    if (content && content !== "") {
                        if ($(content).get(0)) this.$popupObj.find(".lzy_popup_cont").html($(content));//如果时 dom 节点就直接添加
                        else this.$popupObj.find(".lzy_popup_cont").html("<span>" + content + "</span>");//如果是字符串便包括在 span 节点里（适配ie9）
                    }
                    for (i = 0; i < 10; i++) this.$popupObj.removeClass("lzy_popup_close_" + i);
                    if (this.cfg.isShowBg) this.$bgObj.show().css("z-index", getMaxZIndex());

                    this.$popupObj.css("z-index", getMaxZIndex()).show().addClass("lzy_popup_show_" + this.cfg.animation);//显示弹框，插入弹出动画样式
                    setTimeout(function () {
                        that.$popupObj.show();
                    }, 300);
                    return this;
                },
                closePopup: function () {
                    var that = this;
                    that.$popupObj.removeClass("lzy_popup_show_" + this.cfg.animation).addClass("lzy_popup_close_" + this.cfg.animation);//插入弹回动画样式
                    setTimeout(function () {
                        that.$popupObj.hide();//隐藏
                        that.$bgObj.hide();//隐藏
                        try {
                            for (var i = 0; i < that.closeEvents.length; i++) that.closeEvents[i].call(that);//关闭按键回调方法执行
                        } catch (e) {
                            console.error(e);
                        }
                    }, 280);
                    return this;
                },
                addButton: function (btnName, callback) {
                    var that = this, tempColor;
                    var curBtn = $("<button class='lzy_footer_btn'>" + btnName + "</button>").css({borderRadius: this.cfg.borderRadius}).appendTo(this.$popupObj.find(".lzy_popup_footer"))
                        .on("click", function () {
                            if (callback) callback.call(that);
                            if (btnName && (btnName.indexOf("关") >= 0 || btnName.indexOf("消") >= 0)) that.closePopup();//给含有“关闭”意思的按键添加关闭事件
                        })
                        .hover(function () {
                            //给按键添加悬浮样式事件（这里没有使用css来配置是因为主题颜色是活的，所以无法确定按键的颜色是什么）
                            tempColor = $(this).css("background-color");
                            $(this).css('background-color', curBtn.hasClass("lzy_footer_btn_close") ? dimColor(that.cfg.themeColor, .1) : darkColor(that.cfg.themeColor, .92));
                        }, function () {
                            $(this).css('background-color', tempColor);
                        });
                    if (btnName && (btnName.indexOf("关") >= 0 || btnName.indexOf("消") >= 0)) curBtn.addClass("lzy_footer_btn_close");
                    var size = 0, btns = that.$popupObj.find(".lzy_popup_footer").find(".lzy_footer_btn");
                    for (var i = 0; i < btns.length; i++) {
                        var tempSize = btns.eq(i).text().length <= 2 ? 38 : btns.eq(i).text().length * 16;
                        size += tempSize + 22 + parseFloat(btns.eq(i).css('margin-right').replace("px", "")) + parseFloat(btns.eq(i).css('margin-left').replace("px", ""));
                    }
                    if (size > that.$popupObj.width()) that.setStyle({width: size + 20});//添加的按键过多，导致总长超过弹框的宽时便智能设置弹框的宽足与容纳按键
                    return this;
                },
                removeButton: function (nameOrIndex) {
                    if (isNaN(nameOrIndex)) {
                        //非数字即按名称判断按键
                        var btns = this.$popupObj.find(".lzy_popup_footer").find(".lzy_footer_btn");
                        for (var i = 0; i < btns.length; i++) {
                            if (btns.eq(i).text() === nameOrIndex) btns.eq(i).remove();
                        }
                    } else {
                        this.$popupObj.find(".lzy_popup_footer").find(".lzy_footer_btn").eq(nameOrIndex).remove();
                    }
                },
                getStatus: function () {
                    return {
                        status: this.$popupObj.css("display") !== "none",
                        rect: this.$popupObj.get(0).getBoundingClientRect(),
                        version: this.version
                    };
                },
                on: function (type, callback) {
                    var t = type, c = callback;//允许参数位置动态
                    if (typeof type === "function") c = type;
                    if (typeof callback === "string") t = callback;
                    if (t.toLowerCase().indexOf("open") >= 0) this.openEvents.push(c);
                    if (t.toLowerCase().indexOf("close") >= 0) this.closeEvents.push(c);
                    return this;
                }
            };
            return new lzyPopup(cfg);
        }
    });
})(jQuery, window, document);