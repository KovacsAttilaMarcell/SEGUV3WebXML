<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>
  
  <!-- Kulcsképzés típus szerint -->
  <xsl:key name="tipusok" match="auto" use="tipus"/>
  
  <xsl:template match="/">
    <html><body>
        <h2>Autótípusok és darabszámuk</h2>
        
        <table border="1">
          <tr><th>Típus</th><th>Darabszám</th></tr>
          
          <!-- Egyedi típusok -->
          <xsl:for-each select="autok/auto[generate-id() = generate-id(key('tipusok', tipus)[1])]">
            <xsl:sort select="count(key('tipusok', tipus))" data-type="number" order="descending"/>
            
            <tr>
              <td><xsl:value-of select="tipus"/></td>
              <td><xsl:value-of select="count(key('tipusok', tipus))"/></td>
            </tr>
          </xsl:for-each>
          
        </table>
        
      </body></html>
  </xsl:template>
  
</xsl:stylesheet>
