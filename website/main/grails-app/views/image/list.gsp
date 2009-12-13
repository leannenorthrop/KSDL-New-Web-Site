
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="content-admin" />
        <title>Kagyu Samye Dzong London: Manage Images</title>

        <g:javascript library="jquery"/>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery-ui-1.7.2.custom.min.js')}"/>
        <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery.ImageCarousel.js')}"/>
        <link rel="stylesheet" href="${createLinkTo(dir:'css/redmond',file:'jquery-ui-1.7.2.custom.css')}" type="text/css" media="screen" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'ImageCarousel.css')}" type="text/css" media="screen"/>
        <script type="text/javascript">
        var srcImage;
        var show = function(e) {
            var node = $(".imagedisplay");
            node.attr({src: srcImage.replace("thumbnail", "src")});
            node.fadeIn({duration: 500, easing: "easeInOutQuad"});
        };
        var hide = function(e) {
            srcImage = e.target.src;
            $(".imagedisplay").fadeOut({duration: 500, easing: "easeInOutQuad", complete: show});
        };

        $(document).ready(function() {
          $(".carouselBody li a img").mouseenter(function(e){hide(e);});

          $('.carousel').ImageCarousel(  {
              carousel_width: '100%',
              display_header: false,
              carousel_speed: 'normal'
            });
        });
        </script>
    </head>
    <body>
        <div class="content">
            <h1>Image List</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="jquery-ui carousel">
                <div class="carouselBody">
                    <ul>
                        <g:each in="${imageInstanceList}" status="i" var="imageInstance">
                            <li>
                                <g:link action="show" id="${imageInstance.id}" style="width:130px;height:130px;text-align:center;vertical-align:middle;">
                                    <img src="${createLink(controller:'image', action:'thumbnail', id:imageInstance.id)}"/>
                                    <span style="display:block;">${fieldValue(bean:imageInstance, field:'name')}</span>
                                </g:link>
                            </li>
                        </g:each>
                    </ul>
                    <a href="#" class="btnNext" style="color: red;">Next</a>
                    <a href="#" class="btnPrevious" style="color: red;">Previous</a><br/>
                    <div style="height:400px;width:100%;clear:both;margin:0.5em;text-align:center;vertical-align:middle;">
                        <img class="imagedisplay" height="400px" src="${createLink(controller:'image', action:'src', id:imageInstanceList[0].id)}"/>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
