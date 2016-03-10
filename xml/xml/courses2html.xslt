<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:c  ="ustc:courses"
                              exclude-result-prefixes="c xsl">
  <xsl:output method="html" indent="yes"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="c:course">
    <html>
      <body>
        <h1><xsl:value-of select="@courseName" /> (<xsl:value-of select="c:units" />)</h1>
        <xsl:apply-templates select="c:teachers"/>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="c:teachers">
    <ul>
      <xsl:for-each select="c:teacher">
        <li><xsl:value-of select="@familyName" />, <xsl:value-of select="@personalName" /></li>
      </xsl:for-each>
    </ul>
  </xsl:template>
</xsl:stylesheet>