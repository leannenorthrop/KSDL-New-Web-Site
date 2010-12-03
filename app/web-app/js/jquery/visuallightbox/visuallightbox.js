// -----------------------------------------------------------------------------------
//
//    VisualLightBox for jQuery v 1.9.16j
//    http://visuallightbox.com/
//    VisualLightBox is a free wizard program that helps you easily generate LightBox photo
//    galleries, in a few clicks without writing a single line of code. For Windows and Mac!
//    Last updated: 16:37 30.08.2010
//
//
//	The code inserts html at the bottom of the page that looks similar to this:
//
//	<div id="overlay"></div>
//	<div id="lightbox">
//		<table id="outerImageContainer">
//			<tr><td class="tl"></td><td class="tc"></td><td class="tr"></td></tr>
//			<tr><td class="ml"></td>
//			<td id="lightboxFrameBody">
//				<div id="imageContainer">
//					<img id="lightboxImage" />
//					<div id="hoverNav">
//						<a href="javascript:void(0);" id="prevLinkImg">&laquo; prev</a>
//						<a href="javascript:void(0);" id="nextLinkImg">next &raquo;</a>
//					</div>
//					<div id="loading">
//						<a href="javascript:void(0);" id="loadingLink">loading</a>
//					</div>
//				</div>
//			
//				<div id="imageDataContainer">
//					<div id="imageData">
//						<div id="imageDetails">
//							<span id="caption"></span>
//							<span id="numberDisplay"></span>
//							<span id="detailsNav">
//								<a id="prevLinkDetails" href="javascript:void(0);"></a>
//								<a id="slideShowControl" href="javascript:void(0);"></a>
//								<a id="nextLinkDetails" href="javascript:void(0);"></a>
//							</span>
//						</div>
//						<div id="close">
//							<a id="closeLink" href="javascript:void(0);">close</a>
//						</div>
//					</div>
//				</div>
//			</td>
//			<td class="mr"></td></tr>
//			<tr><td class="bl"></td><td class="bc"></td><td class="br"></td></tr>
//		</table>
//	</div>
//
// -----------------------------------------------------------------------------------

//
//	Lightbox Object
//
(function($){
	$.fn.visualLightbox = function(options){
		var activeImage = null,
			badObjects = ['select','object','embed'],
			groupName = null,
			imageArray = [],
			slideShowTimer = null,
			startImage = null,
			descriptionHeight = 50,
			imgPreloader,
			showTimer;
		
		//
		// initialize()
		// Constructor sets class properties and configuration options and
		// inserts html at the bottom of the page which is used to display the shadow 
		// overlay and the image container.
		//
		if (!document.getElementsByTagName){ return; }

		options = $.extend({
			animate : true, // resizing animations
			autoPlay : true, // should slideshow start automatically
			borderSize : 39, // if you adjust the padding in the CSS, you will need to update this variable
			containerID : document, // lightbox container object
			enableSlideshow : true, // enable slideshow feature
			googleAnalytics : false, // track individual image views using Google Analytics
			descSliding: true, // sliding description panel
			imageDataLocation : 'south', // location of image caption information
			closeLocation : '', // location of close button: default in imageData object
			initImage : '', // ID of image link to automatically launch when upon script initialization
			loop : true, // whether to continuously loop slideshow images
			overlayDuration : 0.2, // time to fade in shadow overlay
			overlayOpacity : .7, // transparency of shadow overlay
			prefix : '', // ID prefix for all dynamically created html elements
			classNames : 'vlightbox', // specifies the rel attribute value that triggers lightbox
			resizeSpeed : 7, // controls the speed of the image resizing (1=slowest and 10=fastest)
			showGroupName : false, // show group name of images in image details
			slideTime : 4, // time to display images during slideshow
			strings : { // allows for localization
				closeLink : '',
				loadingMsg : 'loading',
				nextLink : '',
				prevLink : '',
				startSlideshow : '',
				stopSlideshow : '',
				numDisplayPrefix : '',
				numDisplaySeparator : '/'
			},
			enableRightClick: false,// disable right click on images
			featBrowser: true,     // set it to true or false to choose to auto-adjust the maximum size to the browser
			breathingSize: 20,     // control the minimum space around the image box
			startZoom: false,      // do zoom from clicked image while starting
			floating: true         // create floating menu
		}, options);

		if(options.animate){
			var overlayDuration = Math.max(options.overlayDuration,0);
			options.resizeSpeed = Math.max(Math.min(options.resizeSpeed,10),1);
			var resizeDuration = (11 - options.resizeSpeed) * 0.15;
		}else{
			var overlayDuration = 0;
			var resizeDuration = 0;
		}
		
		var enableSlideshow = options.enableSlideshow;
		options.overlayOpacity = Math.max(Math.min(options.overlayOpacity,1),0);
		var playSlides = options.autoPlay;
		var container = $(options.containerID);
		var classNames = options.classNames;
		updateImageList();

		var objBody = container.length && container.get(0) != document ? container.get(0) : document.getElementsByTagName('body').item(0);

		// create additional contaner in begin of document for because
		if (objBody.childNodes.length){
			$(objBody.childNodes[0]).before($('<div></div>'));
			objBody = objBody.childNodes[0];
		};
		
		function connectEvent(obj,name,func){
			$(obj)[name](func);
		};
		
		// create structure
		//------------------
		var objOverlay = document.createElement('div');
		objOverlay.setAttribute('id',getID('overlay'));
		objOverlay.style.display = 'none';
		objBody.appendChild(objOverlay);
		connectEvent(objOverlay,'click',end)
		
		var objLightbox = document.createElement('div');
		objLightbox.setAttribute('id',getID('lightbox'));
		objLightbox.style.display = 'none';
		objBody.appendChild(objLightbox);
		connectEvent(objLightbox,'click',end)
		
		var objImageDataContainer = document.createElement('div');
		objImageDataContainer.setAttribute('id',getID('imageDataContainer'));
		objImageDataContainer.className = getID('clearfix');

		var objOuterImageContainer = document.createElement('table');
		objOuterImageContainer.setAttribute('id',getID('outerImageContainer'));
		objOuterImageContainer.cellSpacing = 0;
		objLightbox.appendChild(objOuterImageContainer);
		
		// create content cells
		var objOICTop = objOuterImageContainer.insertRow(-1);
		var objOICTL =  objOICTop.insertCell(-1);
		objOICTL.className = 'tl';
		var objOICTC =  objOICTop.insertCell(-1);
		objOICTC.className = 'tc';
		var objOICTR =  objOICTop.insertCell(-1);
		objOICTR.className = 'tr';
		var objOICMiddle = objOuterImageContainer.insertRow(-1);
		var objOICML =  objOICMiddle.insertCell(-1);
		objOICML.className = 'ml';
		
		// middle center 
		var objLightboxFrameBody =  objOICMiddle.insertCell(-1);
		objLightboxFrameBody.setAttribute('id',getID('lightboxFrameBody'));
		objLightboxFrameBody.innerHTML= String.fromCharCode(160) //+ '&nbsp;';?
		
		var objOICMR =  objOICMiddle.insertCell(-1);
		objOICMR.className = 'mr';
		var objOICBottom = objOuterImageContainer.insertRow(-1);
		var objOICBL =  objOICBottom.insertCell(-1);
		objOICBL.className = 'bl';
		var objOICBC =  objOICBottom.insertCell(-1);
		objOICBC.className = 'bc';
		var objOICBR =  objOICBottom.insertCell(-1);
		objOICBR.className = 'br';
		

		if(options.imageDataLocation == 'north'){
			objLightboxFrameBody.appendChild(objImageDataContainer);
		}
		
		var objImageData = document.createElement('div');
		objImageData.setAttribute('id',getID('imageData'));
		objImageDataContainer.appendChild(objImageData);
	
		var objImageDetails = document.createElement('div');
		objImageDetails.setAttribute('id',getID('imageDetails'));
		objImageData.appendChild(objImageDetails);
	
		var objCaption = document.createElement('div');
		objCaption.setAttribute('id',getID('caption'));
		objImageDetails.appendChild(objCaption);
	
		var objNumberDisplay = document.createElement('span');
		objNumberDisplay.setAttribute('id',getID('numberDisplay'));
		objImageDetails.appendChild(objNumberDisplay);

		var objDetailsNav = document.createElement('span');
		objDetailsNav.setAttribute('id',getID('detailsNav'));
		objImageDetails.appendChild(objDetailsNav);

		var objPrevLink = document.createElement('a');
		objPrevLink.setAttribute('id',getID('prevLinkDetails'));
		objPrevLink.setAttribute('href','javascript:void(0);');
		objPrevLink.innerHTML = options.strings.prevLink;
		objDetailsNav.appendChild(objPrevLink);
		connectEvent(objPrevLink,'click',showPrev);
		

		var objSlideShowControl = document.createElement('a');
		objSlideShowControl.setAttribute('id',getID('slideShowControl'));
		objSlideShowControl.setAttribute('href','javascript:void(0);');
		objDetailsNav.appendChild(objSlideShowControl);
		connectEvent(objSlideShowControl,'click',toggleSlideShow);


		var objCloseLink = document.createElement('a');
		objCloseLink.setAttribute('id',getID('closeLink'));
		objCloseLink.setAttribute('href','javascript:void(0);');
		objCloseLink.innerHTML = options.strings.closeLink;
		if (options.closeLocation == 'nav')
			objDetailsNav.appendChild(objCloseLink)
		else{
			var objClose = document.createElement('div');
			objClose.setAttribute('id',getID('close'));
			if (options.closeLocation=='top')
				objOICTR.appendChild(objClose)
			else
				objImageData.appendChild(objClose);
			objClose.appendChild(objCloseLink);
		}
		connectEvent(objCloseLink,'click',end);
		
		
		var objNextLink = document.createElement('a');
		objNextLink.setAttribute('id',getID('nextLinkDetails'));
		objNextLink.setAttribute('href','javascript:void(0);');
		objNextLink.innerHTML = options.strings.nextLink;
		objDetailsNav.appendChild(objNextLink);
		connectEvent(objNextLink,'click',showNext);
		
		var objImageContainer = document.createElement('div');
		objImageContainer.setAttribute('id',getID('imageContainer'));
		objLightboxFrameBody.appendChild(objImageContainer);
	
		var objLightboxImage = document.createElement('img');
		objLightboxImage.setAttribute('id',getID('lightboxImage'));
		objImageContainer.appendChild(objLightboxImage);
		
		if (!options.enableRightClick){
			var objHoverNav = document.createElement('div');
			objHoverNav.setAttribute('id',getID('hoverNav'));
			objHoverNav.style.background="white";
			objHoverNav.style.opacity = 0;
			objHoverNav.style.filter = "alpha(opacity=0)";
			objImageContainer.appendChild(objHoverNav);
			connectEvent(objHoverNav,'mousemove',hoverNav);
			connectEvent(objHoverNav,'mouseout',outNav);
		}
		
		var objPrevLinkImg = document.createElement('a');
		objPrevLinkImg.setAttribute('id',getID('prevLinkImg'));
		objPrevLinkImg.setAttribute('href','javascript:void(0);');
		objImageContainer.appendChild(objPrevLinkImg);
		connectEvent(objPrevLinkImg,'click',showPrev);
		
		var objNextLinkImg = document.createElement('a');
		objNextLinkImg.setAttribute('id',getID('nextLinkImg'));
		objNextLinkImg.setAttribute('href','javascript:void(0);');
		objImageContainer.appendChild(objNextLinkImg);
		connectEvent(objNextLinkImg,'click',showNext);

		var objLoading = document.createElement('div');
		objLoading.setAttribute('id',getID('loading'));
		objImageContainer.appendChild(objLoading);
	
		var objLoadingLink = document.createElement('a');
		objLoadingLink.setAttribute('id',getID('loadingLink'));
		objLoadingLink.setAttribute('href','javascript:void(0);');
		objLoadingLink.innerHTML = options.strings.loadingMsg;
		objLoading.appendChild(objLoadingLink);
		connectEvent(objLoadingLink,'click',end);
		
		if(options.imageDataLocation != 'north')
			objLightboxFrameBody.appendChild(objImageDataContainer);
		
		if(options.initImage != ''){
			start('#'+options.initImage);
		}

		function getHref(Node){
			if (Node.tagName.toLowerCase() != 'a') Node = $('A:first' ,Node);
			return $(Node).attr('href');
		};

		function getTitle(Node){	
			if(Node.tagName.toLowerCase()=='a') return $(Node).attr('title') || Node.title;
			return $('>*:last',Node).html()
		};
		
		//
		//	updateImageList()
		//	Loops through specific tags within 'container' looking for 
		// 'lightbox' references and applies onclick events to them.
		//
		function updateImageList(){
			$('.'+classNames.replace(/^\,/,'.$&'), container).each(function(){
				if (getHref(this)){
					$(this).click(function(event){
						event.preventDefault();
						start(this); 
						return false;
					});
				}
			})
		}

		
		// watermark
		var t = ''; // VisualLightBox.com
		if (t){
			var c = $('<div></div>');
			
			c.css({
				position:'absolute',
				right:'0px',
				bottom:'0px',
				padding:'2px 3px',
				'background-color':'#EEE',
				'z-index':10
			});
			$(objImageContainer).append(c);

	
			var d = $(document.createElement("A"));
			d.css({
				color:'#555',
				font:'11px Arial,Verdana,sans-serif',
				padding:'3px 6px',
				width:'auto',
				height:'auto',
				margin:'0 0 0 0',
				outline:'none'
			});
			d.attr({href:'http://'+ t.toLowerCase()});
			d.html(t);
			d.bind('contextmenu', function(eventObject){
				return false;
			});
			
			c.append(d);
		}
		
		
		//
		//	start()
		//	Display overlay and lightbox. If image is part of a set, add siblings to imageArray.
		//
		var start = this.start = function (imageLink) {

			hideBadObjects();

			imageLink = $(imageLink);
			// stretch OVERLAY to fill page and fade in
			$$('overlay').css({height:docWH()[1]+'px'});
			
		
			if (options.descSliding)
				$$('imageDataContainer').hide();
				
			// set start position and size = imageLink
			$$('lightboxImage').hide().attr({src: ''});
			
			
			// if start with zoom then set position and size from source link
			if (options.startZoom){
				$$('imageContainer').css({
					width: imageLink.width()  + 'px',
					height: imageLink.height()  + 'px'
				});
			
				// no fading for ie
				if (!document.all) $$('outerImageContainer').css({opacity: 0.1 });

				
				$$('lightbox').css({
					left: imageLink.offset().left - options.borderSize + 'px',
					top: imageLink.offset().top - options.borderSize + 'px',
					width: imageLink.width() + options.borderSize*2 + 'px', //here need width:'auto', but auto value don't work for ie < 8
					height: 'auto'
				});
			}
			else{
				// OVERLAY fade in
				$$('overlay').css({opacity:0}).show().fadeTo(overlayDuration*1000, options.overlayOpacity);
				
				$$('lightbox').css({
					left:0, 
					width:'100%'
				})
			}
			
			$$('lightbox').show();

			// create image group array
			imageArray = [];
			groupName = null;
			startImage = 0;

			// loop through anchors, find other images in group, and add them to imageArray
			$('.'+(imageLink.attr('className') || imageLink.get(0).className), container).each(function(){
				if (getHref(this)){
					imageArray.push({link: getHref(this), title: getTitle(this)});
					if(this == imageLink.get(0)){
						startImage = imageArray.length-1;
					}
				}
			});
			
			// get group name
			if (imageArray.length>1)
				groupName = imageLink.attr('className');

			
			if (options.featBrowser) 
				$(window).resize(adjustImageSizeNoEffect);
			if (options.floating)
				$(window).scroll(adjustImageSizeNoEffect);
			
			$(window).resize(adjustOverlay);
			$(window).scroll(adjustOverlay);


			changeImage(startImage);
		};
		
		//
		//	changeImage()
		//	Hide most elements and preload image in preparation for resizing image container.
		//
		function changeImage(imageNum){
			activeImage = imageNum;

			disableKeyboardNav();
			pauseSlideShow();

			// hide elements during transition
			showLoading();
			if (!options.startZoom) $$('lightboxImage').hide();
			$$('prevLinkImg').hide();
			$$('nextLinkImg').hide();
			
			if (options.descSliding){
				$$('imageDataContainer').hide();
				//$$('numberDisplay').hide();
				//$$('detailsNav').hide();
			};
			
			imgPreloader = new Image();
			
			// once image is preloaded, resize image container
			imgPreloader.onload=function(){
				imageArray[activeImage].link = imgPreloader.src; // cache url from src (may be a new format)
				
				// save image proportion
				imageArray[activeImage].width = imgPreloader.width;
				imageArray[activeImage].height = imgPreloader.height;
				adjustImageSize(false);
			}
			
			if (options.startZoom  && !$$('lightboxImage').attr('src')){
				imageArray[activeImage].width  = 320;
				imageArray[activeImage].height  = 240;
				adjustImageSize(false, true);
			};
			
			imgPreloader.src = imageArray[activeImage].link;
			
			if(options.googleAnalytics){
				urchinTracker(imageArray[activeImage].link);
			}
		};

		//
		//  adjustImageSize()
		//  adjust image size if option featBrowser is set to true
		//
		function adjustImageSize( recall, noImage ) {
			// get image size
			var imgWidth = imageArray[activeImage].width;
			var imgHeight = imageArray[activeImage].height;
			var arrayPageSize = getPageSize();
			var imageProportion = imgWidth / imgHeight;
			
			// adjust image size if featBrowser option is set to true
			if (options.featBrowser) {
			  // calculate proportions 
			  var winProportion = arrayPageSize.winWidth / arrayPageSize.winHeight;

			  if (imageProportion > winProportion) {
				// calculate max width base on page width
				var maxWidth = arrayPageSize.winWidth - options.borderSize * 2 - options.breathingSize * 2;
				var maxHeight = Math.round(maxWidth / imageProportion);
			  } else {
				// calculate maw height base on page height
				var maxHeight = arrayPageSize.winHeight - options.borderSize * 2 - options.breathingSize * 2 - descriptionHeight;
				var maxWidth = Math.round(maxHeight * imageProportion);
			  }
			  if (imgWidth > maxWidth || imgHeight > maxHeight) {
				imgWidth = maxWidth;
				imgHeight = maxHeight;
			  }
			};


			var imgTop = getPageScroll().y + (getPageSize().winHeight - (imgHeight + descriptionHeight + options.borderSize * 2) )/2;
			
			
			var imageContainer = $$('imageContainer');
			
			if (recall == true){
				// instant set size and pos
				imageContainer.css({ height: imgHeight + 'px', width: imgWidth + 'px'});
				
				// restore container while start and correct its top position
				if (options.floating){// dublicate from resizeImageContainer()
					moveEffect($$('lightbox'), imgTop)
				}
				else
					$$('lightbox').css({top:imgTop + 'px'});
			
			
			} else {
				
				var lightboxImage = $$('lightboxImage');
				
				// stop animations!! for example a user force click to next image
				imageContainer.stop(true,false);
				lightboxImage.stop(true,false);

				// change image by loaded
				// backup old image for cross-fade 
				var lightboxImage2;
				if (options.startZoom && lightboxImage.attr('src')){
					lightboxImage2 = lightboxImage;
					lightboxImage2.attr({id:getID('lightboxImage2')});
				}
				else
					lightboxImage.remove();
					
				// create new image
				if (!noImage){
					lightboxImage = $(imgPreloader);
					lightboxImage.hide();
					lightboxImage.attr({id:getID('lightboxImage')});
					imageContainer.append(lightboxImage);
				}

				
				// calc new image dimension by old image dimension =  (w1/h1) / (w0/h0)
				with (imageContainer)
					var resizeFactor = imageProportion / (width()/ height());
				

				if (!noImage){
				
					// if SOFT change image by ZOOMING
					if (options.startZoom){
						// stop animations!!
						if(lightboxImage2) $$('lightboxImage2').stop(true,true);
					


						var zoomF = lightboxImage2? 120: 100;

						
						// prepare prev image (if exists) to cross-fade 
						// zoom old image with fixed one of side while change proportion
						// size value by default =100%, but calced for some case
						if (lightboxImage2)
							with(lightboxImage2){
								css({
									width : (1 > resizeFactor? 'auto': (width() / parent().width())*100 + '%'),
									height: (1 > resizeFactor? (height() / parent().height())*100 + '%': 'auto'),
									left: 0, top: 0
								});
							};

						

						// if slideshow then RESIZE image 150..100 or 100..150 
						// else normal transform
						lightboxImage.css({
							opacity: 0,
							display: 'block',
							position:'absolute',
							width : (1 > resizeFactor)? zoomF + '%': 'auto', 
							height: (1 > resizeFactor)? 'auto': zoomF +'%',
							left  : (100 - zoomF*(1 > resizeFactor? 1: resizeFactor  ))/2 + '%',
							top   : (100 - zoomF*(1 > resizeFactor? 1/(resizeFactor): 1))/2 + '%'
						});
					};
					
					// start changing image with effects
					if (options.startZoom) hideLoading();
				}
				
				resizeImageContainer(imgTop, imgWidth, imgHeight, resizeFactor, noImage);
			}


			// force set width because width:100% widening lightbox container with background color gap
			//$$('imageDataContainer').css({width: imgWidth + 'px'});
			
			if (options.enableRightClick){
				$$('lightboxImage').mousemove(hoverNav);
				$$('lightboxImage').mouseout(outNav);
			}
			
		};

		//
		//	resizeImageContainer()
		//	soft change image size and position
		//	@imgTop new top position of imageContainer
		//	@imgWidth, @imgHeight - new size of image container
		//
		function resizeImageContainer(imgTop, imgWidth, imgHeight, resizeFactor, noImage) {

			// cash object for synchro start and speed up
			var imageContainer = $$('imageContainer');
			var lightboxImage = $$('lightboxImage');
			var lightbox = $$('lightbox');
			if (!noImage) var lightboxImage2 = $$('lightboxImage2');
			
			
			// if need zoom then slow fade-in image while resize, async
			if(options.startZoom){
				lightboxImage.fadeTo(resizeDuration*1000, 1);

				// show or fade-in lightbox while starting slideshow
				if(!document.all) $$('outerImageContainer').fadeTo( resizeDuration*1000, 1 );
			};
				
			
			
			// restore container while start and correct its top position
			moveEffect(lightbox, imgTop);
			

			if(options.startZoom && !noImage){
			
				// hiding prev image asinc with zoom-in
				lightboxImage2.animate(
					$.extend(
						{ opacity: 0 },
						resizeFactor<1?
							{ height:'120%', top:'-10%', left: (100-120/resizeFactor)/2 + '%' }:
							{ width:'120%', left:'-10%', top: (100-resizeFactor*120)/2 + '%' }
					),
					{ queue:false, duration:resizeDuration * 1000, complete: function(){$(this).remove()}}
				);
				
				
				// move lightboxImage to zero normal position
				lightboxImage.animate(
					$.extend(
						{ left: 0, top: 0 },
						resizeFactor<1? {width: '100%'}: {height: '100%'}
					),
					{
						queue:false,
						duration:resizeDuration * 1000
					}
				);
			}
			
			
			// calculate size difference between new and old image, and resize if necessary
			// scalars based on change from old to new
			imageContainer.animate(
				{ width: imgWidth + 'px', height: imgHeight + 'px' },
				{ queue:false, duration:resizeDuration * 1000, complete: !noImage? function (){showImage()}: null }
			);
		};
		
		
		// soft restoring lightbox position
		function moveEffect(lightbox, imgTop){
		
			// restore container while start and correct its top position
			lightbox.stop(true,false);
			lightbox.animate(
				{ width:'100%', left:0, top:imgTop },
				{ queue:false, duration:resizeDuration * 1000 }
			);
		};
		
		
		// control by loading show/hide
		// show after few time for prevent of flickering
		function showLoading(){
			clearTimeout(showTimer);
			var loading = $$('loading');
			loading.show();
			loading.css({ visibility:'hidden'});
			
			showTimer = setTimeout(
				function(){$$('loading').css({visibility:'visible'})},
				300);
		}
		function hideLoading(){
			clearTimeout(showTimer);
			$$('loading').hide();
		}

		
		//
		//	showImage()
		//	Display image and begin preloading neighbors.
		//
		function showImage(){
			hideLoading(); // in zoom image shall be hidden
			
			if (options.startZoom){
				// OVERLAY fade in
				$$('overlay:hidden').css({opacity:0}).show().fadeTo(overlayDuration*1000, options.overlayOpacity);
				
				showDetails();
			}
			else
				$$('lightboxImage').css({opacity:0}).show().fadeTo(500, 1, function(){ showDetails();});
			
			preloadNeighborImages();
		};

		//
		//	updateDetails()
		//	Display caption, image number, and bottom nav.
		//
		function updateDetails() {
			$$('caption').html(imageArray[activeImage].title || '');
			
			// if image is part of set display 'Image x of y' 
			if(imageArray.length > 1){
				var num_display = options.strings.numDisplayPrefix + ' ' + eval(activeImage + 1) + ' ' + options.strings.numDisplaySeparator + ' ' + imageArray.length;
				if(options.showGroupName && groupName){
					num_display += ' '+options.strings.numDisplaySeparator+' '+groupName;
				}
				$$('numberDisplay').text(num_display);
				$$('slideShowControl').css({display: enableSlideshow?'':'none'});
			}
		};
		
		function showDetails(){
			updateDetails();

			if (options.descSliding){
				$$('imageDataContainer').animate(
					{	height:'show',
						opacity:'show' },
					650, null, function() { updateNav();}
				);
			}
			else
				updateNav();
		};
		
		//
		//	updateNav()
		//	Display appropriate previous and next hover navigation.
		//
		function updateNav() {
			// restore imageDataContainer size for avtomaticaly change with its contaner
			var d = 1/imageArray.length;
			descriptionHeight = descriptionHeight*(1-d) + $$('imageDataContainer').height()*d;
			
			if(imageArray.length > 1){
				$$('prevLinkImg').show();
				$$('nextLinkImg').show();
				
				if(enableSlideshow){
					if(playSlides){
						startSlideShow();
					} else {
						stopSlideShow();
					}
				}
			}
			enableKeyboardNav();
		};
		//
		//	startSlideShow()
		//	Starts the slide show
		//
		function startSlideShow(){
			if ($$('lightbox:hidden').length) return;

			pauseSlideShow();
			
			playSlides = true;
			slideShowTimer = setTimeout(function(){ showNext() }, options.slideTime * 1000);
			
			$$('slideShowControl').text(options.strings.stopSlideshow);
			$$('slideShowControl').addClass('started');
		};
		
		//
		//	stopSlideShow()
		//	Stops the slide show
		//
		function stopSlideShow(){
			playSlides = false;
			pauseSlideShow();
			
			$$('slideShowControl').text(options.strings.startSlideshow);
			$$('slideShowControl').removeClass('started');
		};

		//
		//	stopSlideShow()
		//	Stops the slide show
		//
		function toggleSlideShow(){
			if(playSlides){
				stopSlideShow();
			}else{
				startSlideShow();
			}
		};

		//
		//	pauseSlideShow()
		//	Pauses the slide show (doesn't change the value of playSlides)
		//
		function pauseSlideShow(){
			if(slideShowTimer)
				slideShowTimer = clearTimeout(slideShowTimer);
		};
		
		//
		//	showNext()
		//	Display the next image in a group
		//
		function showNext(){
			if(imageArray.length > 1){
				pauseSlideShow();
				
				if(!options.loop && ((activeImage == imageArray.length - 1 && startImage == 0) || (activeImage+1 == startImage))){
					end();
					return;
				}
				if(activeImage == imageArray.length - 1){
					changeImageWithData(0);
				}else{
					changeImageWithData(activeImage+1);
				}
			}
		};

		//
		//	changeImageWithData()
		//	hide image data before changeImage
		//
		function changeImageWithData(imageNum){
			if (options.descSliding)
				$$('imageDataContainer').animate(
					{	height:'hide',
						opacity:'hide' },
					650, null, function() { changeImage(imageNum) }
				);
			else
				 changeImage(imageNum);
		}
		
		
		//
		//	showPrev()
		//	Display the next image in a group
		//
		function showPrev(){
			if(imageArray.length > 1){
				if(activeImage == 0){
					changeImageWithData(imageArray.length - 1);
				}else{
					changeImageWithData(activeImage-1);
				}
			}
		};
		
		//
		//	showFirst()
		//	Display the first image in a group
		//
		function showFirst(){
			if(imageArray.length > 1){
				changeImageWithData(0);
			}
		};

		//
		//	showFirst()
		//	Display the first image in a group
		//
		function showLast(){
			if(imageArray.length > 1){
				changeImageWithData(imageArray.length - 1);
			}
		};

		//
		//	enableKeyboardNav()
		//
		function enableKeyboardNav() {
			document.onkeydown = keyboardAction; 
		};

		//
		//	disableKeyboardNav()
		//
		function disableKeyboardNav() {
			document.onkeydown = '';
		};

		//
		//	keyboardAction()
		//
		function keyboardAction(e) {
			if (e == null) { // ie
				keycode = event.keyCode;
			} else { // mozilla
				keycode = e.which;
			}

			key = String.fromCharCode(keycode).toLowerCase();
			
			if(key == 'x' || key == 'o' || key == 'c'){ // close lightbox
				end();
			} else if(key == 'p' || key == '%'){ // display previous image
				showPrev();
			} else if(key == 'n' || key =='\''){ // display next image
				showNext();
			} else if(key == 'f'){ // display first image
				showFirst();
			} else if(key == 'l'){ // display last image
				showLast();
			} else if(key == 's'){ // toggle slideshow
				if(imageArray.length > 0 && options.enableSlideshow){
					toggleSlideShow();
				}
			}
		};

		//
		//	preloadNeighborImages()
		//	Preload previous and next images.
		//
		function preloadNeighborImages(){
			var nextImageID = imageArray.length - 1 == activeImage ? 0 : activeImage + 1;
			(new Image()).src = imageArray[nextImageID].link

			var prevImageID = activeImage == 0 ? imageArray.length - 1 : activeImage - 1;
			(new Image()).src = imageArray[prevImageID].link;
		};

		//
		//	end()
		//
		function end(Event) {
			if (Event){
				var id = $(Event.target).attr('id');
				if (getID('closeLink') != id && getID('lightbox') != id && getID('overlay') != id) return;
			};
			
			$$('imageContainer').stop(true,false);
			$$('lightboxImage').stop(true,false);
			imgPreloader.onload = null;
			
			disableKeyboardNav();
			pauseSlideShow();
			$$('lightbox').hide();
			
			// do before fadeOut because in Opera effects are stoped
			showBadObjects();
			
			if (options.overlayOpacity)
				$$('overlay').fadeOut(overlayDuration*1000)
			else 
				$$('overlay').hide();
			
			
			// unbind event handler
			$(window).unbind('resize', adjustImageSizeNoEffect);
			$(window).unbind('scroll', adjustImageSizeNoEffect);
			$(window).unbind('resize', adjustOverlay);
			$(window).unbind('scroll', adjustOverlay);
			
		};
		
		//
		//	show navigate arrow on mouse move
		//
		function hoverNav(event){
			if (event.pageX - $$('imageContainer').offset().left < $$('imageContainer').width()/2){
				$$('prevLinkImg').addClass('hover');
				$$('nextLinkImg').removeClass('hover');
			}
			else{
				$$('prevLinkImg').removeClass('hover');
				$$('nextLinkImg').addClass('hover');
			}
		};
		
		function outNav(){
			$$('prevLinkImg').removeClass('hover');
			$$('nextLinkImg').removeClass('hover');
		};		
		
		
		function adjustImageSizeNoEffect(){
			adjustImageSize(true);
		}

		
		function adjustOverlay(){
			$$('overlay').css({
				left: getPageScroll().x+'px', top: 0, width: '100%', 
				height: docWH()[1] +'px'
			});
		}
		
		//
		//	showBadObjects()
		//
		function showBadObjects(){
			var els;
			var tags = badObjects;
			for(var i=0; i<tags.length; i++){
				els = document.getElementsByTagName(tags[i]);
				for(var j=0; j<els.length; j++){
					$(els[j]).css({visibility:'visible'});
				}
			}
		};
		
		//
		//	hideBadObjects()
		//
		function hideBadObjects(){
			var tags = badObjects;
			for(var i=0; i<tags.length; i++)
				$(tags[i]).css({visibility:'hidden'});
		};

		
		//
		// getPageScroll()
		// Returns array with x,y page scroll values.
		// Core code from - quirksmode.org
		//
		function getPageScroll(){
			var x,y;
			if (self.pageYOffset) {
				x = self.pageXOffset;
				y = self.pageYOffset;
			} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
				x = document.documentElement.scrollLeft;
				y = document.documentElement.scrollTop;
			} else if (document.body) {// all other Explorers
				x = document.body.scrollLeft;
				y = document.body.scrollTop;
			}
			return {x:x,y:y};
		};
	
		//
		// getPageSize()
		// Returns array with window width, height
		//
		function getPageSize(){
			var windowX,windowY;

			if (self.innerHeight) {	// all except Explorer
				windowX = self.innerWidth;
				windowY = self.innerHeight;
			} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
				windowX = document.documentElement.clientWidth;
				windowY = document.documentElement.clientHeight;
			} else if (document.body) { // other Explorers
				windowX = document.body.clientWidth;
				windowY = document.body.clientHeight;
			}	

			return {winWidth:windowX,winHeight:windowY};
		};
		
		// return max page width/height for overlay
		function docWH(){
			var b=document.body, e=document.documentElement, w=0,h=0;
			if (e) {
				w=Math.max(w, e.scrollWidth, e.offsetWidth);
				h=Math.max(h, e.scrollHeight, e.offsetHeight);
			}
			if (b) {
				w=Math.max(w, b.scrollWidth, b.offsetWidth);
				h=Math.max(h, b.scrollHeight, b.offsetHeight);

				// for opera 9.5+ strict
				if (window.innerWidth){
					w=Math.max(w, window.innerWidth);
					h=Math.max(h, window.innerHeight);
				}
			}
			return [w,h];
		};
		

		//
		// getID()
		// Returns formatted Lightbox element ID
		// use only prefix becase some line using this function with suffux!
		// 
		function getID(id){
			return options.prefix+id;
		};
		
		//
		// $$()
		// return engine object with id as formatted Lightbox
		//
		function $$(name){
			return $('#' + getID(name));
		}

		
		return this
	}

})(jQuery);

