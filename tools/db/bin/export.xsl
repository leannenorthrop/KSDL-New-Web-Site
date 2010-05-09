<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="2.0"
                xmlns:b64="java:org.samye.dzong.london.Base64Wrapper">
    <xsl:output method="text"/>
    <xsl:template match="/">
        <xsl:for-each select="//table">
            <xsl:variable name="filename" select="concat('target/csv/',@name,'.csv')"/>
            <xsl:value-of select="$filename"/>
            <!-- Creating  -->
            <xsl:result-document href="{$filename}">
                <xsl:for-each select="column">
                    <xsl:value-of select="."/>
                    <xsl:if test="position() != last()">
                        <xsl:value-of select="','"/>
                    </xsl:if>
                </xsl:for-each>
                <xsl:text>&#10;</xsl:text>
                <xsl:apply-templates select="row"/>
            </xsl:result-document>
        </xsl:for-each>
    </xsl:template>
    <xsl:template match="row">
        <xsl:for-each select="value|null">
            <xsl:variable name="v" select="."/>
            <xsl:variable name="t" select="tokenize($v, '&#xA;')"/>
            <xsl:variable name="isBase64" select="ends-with($v, '=') or (string-length($t[1]) = 76 and matches($v, '[A-Za-z0-9=+/]', 'x'))"/>
            <xsl:choose>
                <xsl:when test="$isBase64">
                    <xsl:value-of select="b64:decodeToHex($v)"/>
                </xsl:when>
                <xsl:when test="contains($v, ',')">
                    <xsl:text>"</xsl:text><xsl:value-of select="translate($v,',', '‚')"/><xsl:text>"</xsl:text>
                </xsl:when>
                <xsl:when test="string-length(.) &gt; 0">
                    <!--xsl:value-of select="translate(normalize-space(.), '  ', '')"/-->
                    <xsl:value-of select="$v"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:text>null</xsl:text>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="position() != last()">
                <xsl:value-of select="','"/>
            </xsl:if>
        </xsl:for-each>
        <xsl:text>&#10;</xsl:text>
    </xsl:template>
</xsl:stylesheet>
