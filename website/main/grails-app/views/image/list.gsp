
<%@ page import="org.samye.dzong.london.media.Image" %>
<html>
    <head>
        <meta name="layout" content="content-admin" />
        <title>Manage Images</title>
        <link rel="stylesheet" href="${resource(dir:'css/site',file:'imageCarousel.css')}" media="screen, projection" />
        <g:javascript src="jquery/jquery.ImageCarousel.js"/>
    <g:javascript>
        var srcImage;
        var title;
        var show = function(e) {
            var node = $(".imagedisplay");
            var imgNode = $(".imagedisplay img");
            var titleNode = $(".imagedisplay h3");
            imgNode.attr({src: srcImage.replace("thumbnail", "src")});
            titleNode.html(title);
            node.fadeIn({duration: 500, easing: "easeInOutQuad"});
        };
        var hide = function(e) {
            srcImage = e.target.src;
            title = e.target.title;
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
        </g:javascript>
    </head>
    <body>
        <div class="content">
            <h1>Image List</h1>
            <p>Hover mouse over thumbnail images to preview, click thumbnail to edit tags. Use left and right arrow buttons to scroll through images.</p>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="jquery-ui carousel">
                <div class="carouselBody">
                    <ul>
                        <g:each in="${imageInstanceList}" status="i" var="imageInstance">
                            <li>
                                <g:link action="edit" id="${imageInstance.id}" style="width:130px;height:130px;text-align:center;vertical-align:middle;">
                                    <img src="${createLink(controller:'image', action:'thumbnail', id:imageInstance.id)}" alt="${fieldValue(bean:imageInstance, field:'name')}" title="${fieldValue(bean:imageInstance, field:'name')}"/>
                                </g:link>
                            </li>
                        </g:each>
                    </ul>
                    <a href="#" class="btnNext" style="color: red;">Next</a>
                    <a href="#" class="btnPrevious" style="color: red;">Previous</a><br/>
                    <div class="imagedisplay" style="height:420px;width:100%;clear:both;margin:0.5em;text-align:center;vertical-align:middle;">
                        <h3></h3>
                        <img height="400px" src="${createLink(controller:'image', action:'src', id:imageInstanceList[0]?.id)}"/>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
