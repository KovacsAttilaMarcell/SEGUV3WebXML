<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>
  
  <xsl:key name="varosok" match="auto" use="tulaj/varos"/>
  
  <xsl:template match="/">
    <html><body>
        <h2>Városonkénti autódarabszám és összérték</h2>
        
        <table border="1">
          <tr><th>Város</th><th>Darabszám</th><th>Összár</th></tr>
          
          <xsl:for-each select="autok/auto[generate-id() = generate-id(key('varosok', tulaj/varos)[1])]">
            <tr>
              <td><xsl:value-of select="tulaj/varos"/></td>
              <td><xsl:value-of select="count(key('varosok', tulaj/varos))"/></td>
              <td>
                <xsl:value-of select="sum(key('varosok', tulaj/varos)/ar)"/>
              </td>
            </tr>
          </xsl:for-each>
          
        </table>
        
      </body></html>
  </xsl:template>
  
</xsl:stylesheet>
