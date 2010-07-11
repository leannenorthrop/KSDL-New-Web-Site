<html>
    <head>
        <title><g:message code="shop.title"/></title>
        <meta name="layout" content="shopTemplate">     
    </head>
    <body>  
        <g:render template="/toparticles" model="[articles:topArticles]"/>    
        <div class="clear"></div> 
        <div class="grid">
            <div class="grid_8">
                <g:render template="/productList" model="[heading:'product.isnew.label', products:newProducts,total:totalNewProducts]"/>
            </div>
            <div class="grid_8">
                <g:render template="/productList" model="[heading:'product.isDiscount.label', products:discountedProducts,total:totalDiscountedProducts]"/>
            </div>
            <div class="clear"></div>  
        </div>
    </body>
</html>
