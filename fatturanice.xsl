<?xml version="1.0"?>
<xsl:stylesheet 
	version="1.1" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="html" indent="yes"/>
	<xsl:template match="/">

	

	<html>
		  <head>
		    <meta charset="utf-8"/>
		    <title>FatturaPA</title>
		    <link rel="stylesheet" href="style/style.css" media="all" />
		  </head>
		  <body>

		  	<main>
		  		<xsl:call-template name="header"/>
			  	<xsl:for-each select="//FatturaElettronicaBody">
			  		<xsl:call-template name="body"/>
			  	</xsl:for-each>
		  	</main>

		  </body>
	</html>	
	</xsl:template>



	<xsl:template name="header">
	    <header class="clearfix">
	      	<div id="logo">
	        	<img src="style/logo.png"/>
	      	</div>
	      	<h1>FatturaPA Elettronica</h1>



	      	<div id="company" class="clearfix">
				<xsl:if test="//CedentePrestatore/DatiAnagrafici/Anagrafica/Denominazione">
			        <div>
			        	<xsl:value-of select="//CedentePrestatore/DatiAnagrafici/Anagrafica/Denominazione"/>
			        </div>
			    </xsl:if>

				<xsl:if test="//CedentePrestatore/IdFiscaleIVA/IdCodice">
			        <div>
			        	<xsl:value-of select="//CedentePrestatore/IdFiscaleIVA/IdCodice"/>
			        </div>
			    </xsl:if>
				<xsl:if test="//CedentePrestatore/Sede">
			        <div>
			        	<xsl:if test="//CedentePrestatore/Sede/Indirizzo">
			        		<xsl:value-of select="//CedentePrestatore/Sede/Indirizzo"/>
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CedentePrestatore/Sede/Comune">
			        		<xsl:value-of select="//CedentePrestatore/Sede/Comune"/>
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CedentePrestatore/Sede/CAP">
			        		<xsl:value-of select="//CedentePrestatore/Sede/CAP"/>
			        	</xsl:if>
			        	,<br/>
			        	<xsl:if test="//CedentePrestatore/Sede/Provincia">
			        		(<xsl:value-of select="//CedentePrestatore/Sede/Provincia"/>)
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CedentePrestatore/Sede/Nazione">
			        		<xsl:value-of select="//CedentePrestatore/Sede/Nazione"/>
			        	</xsl:if>
			        </div>
			    </xsl:if>
			    <xsl:if test="//DatiTrasmissione/ContattiTrasmittente/Email">
			    	<div>
				    	<xsl:value-of select="//DatiTrasmissione/ContattiTrasmittente/Email"/>
			        </div>
			    </xsl:if>

			    <xsl:if test="//DatiTrasmissione/ContattiTrasmittente/Telefono">
			    	<div>
				    	<xsl:value-of select="//DatiTrasmissione/ContattiTrasmittente/Telefono"/>
			        </div>
			    </xsl:if>
	      	</div>
	     	<div id="project">
				<xsl:if test="//CessionarioCommittente/DatiAnagrafici/Anagrafica/Denominazione">
			        <div>
			        	<span>CLIENTE</span>
			        	<xsl:value-of select="//CessionarioCommittente/DatiAnagrafici/Anagrafica/Denominazione"/>
			        </div>
			    </xsl:if>

				<xsl:if test="//CessionarioCommittente/DatiAnagrafici/CodiceFiscale">
			        <div>
			        	<span>CF</span>
			        	<xsl:value-of select="//CessionarioCommittente/DatiAnagrafici/CodiceFiscale"/>
			        </div>
			    </xsl:if>

				<xsl:if test="//CessionarioCommittente/Sede">
			        <div>
			        	<span>INDIRIZZO</span>
			        	<xsl:if test="//CessionarioCommittente/Sede/Indirizzo">
			        		<xsl:value-of select="//CessionarioCommittente/Sede/Indirizzo"/>
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CessionarioCommittente/Sede/Comune">
			        		<xsl:value-of select="//CessionarioCommittente/Sede/Comune"/>
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CessionarioCommittente/Sede/CAP">
			        		<xsl:value-of select="//CessionarioCommittente/Sede/CAP"/>
			        	</xsl:if>
			        	,<br/>
			        	<span></span>
			        	<xsl:if test="//CessionarioCommittente/Sede/Provincia">
			        		(<xsl:value-of select="//CessionarioCommittente/Sede/Provincia"/>)
			        	</xsl:if>
			        	<xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text>
			        	<xsl:if test="//CessionarioCommittente/Sede/Nazione">
			        		<xsl:value-of select="//CessionarioCommittente/Sede/Nazione"/>
			        	</xsl:if>
			        </div>
			    </xsl:if>

	      	</div>
	    </header>
	</xsl:template>



	<xsl:template name="body">
			<br/>
			<hr/>
			<h2>Fattura n°
				<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
			</h2>
				<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Data">
					<p>
						<b>Data documento</b>:
						<span>
							<xsl:call-template name="FormatDate">
								<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
							</xsl:call-template>
						</span>
					</p>

				</xsl:if>

			<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
				
				<p><b>Causale</b>:</p>
				<p>
					<xsl:value-of select="current()" />
				</p>				
			</xsl:for-each>


			<p>
				<b>Valuta</b>:
				<span>
					<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa" />
				</span>
			</p>


		      <table>
		        <thead>
		          <tr>
		            <th class="desc">DESCRIZIONE</th>
		            <th>PR.UNITARIO</th>
		            <th>QT</th>
		            <th>IVA</th>
		            <th>TOTALE</th>
		          </tr>
		        </thead>
		        <tbody>
				<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
		          <tr>
		            <td class="desc">
		            	<xsl:value-of select="Descrizione"/>
		            </td>
		            <td class="unit">
		            	<xsl:value-of select="PrezzoUnitario"/>
		            </td>
		            <td class="qty">
		            	<xsl:value-of select="Quantita"/>
		            </td>
		            <td class="qty">
		            	<xsl:value-of select="AliquotaIVA"/>
		            </td>
		            <td class="total">
		            	<xsl:value-of select="PrezzoTotale"/>
		            </td>
		          </tr>
		        </xsl:for-each>
		        <xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
		        <tr>
		            <td colspan="4"> IVA 
		            	<xsl:value-of select="AliquotaIVA"/>%
		            </td>
		            <td class="total">
		            	<xsl:value-of select="Imposta"/>
		            </td>
         		 </tr>
		        <tr>
		            <td colspan="4">Imponibile (IVA 
		            	<xsl:value-of select="AliquotaIVA"/>%)
		            </td>
		            <td class="total">
		            	<xsl:value-of select="ImponibileImporto"/>
		            </td>
         		 </tr>
         		</xsl:for-each>

         		<xsl:for-each select="DatiPagamento">
	         		<tr>
			            <td colspan="4" class="grand total">Totale</td>
			            <td class="grand total">
			            	<xsl:value-of select="DettaglioPagamento/ImportoPagamento"/>
			            </td>
			        </tr>
         		</xsl:for-each>

		        </tbody>
		      </table>
		      <xsl:if test="DatiGenerali/DatiTrasporto">
			      <div id="notices">
			        <h4>Trasporto:</h4>
			        <div class="notice">
			        		<xsl:value-of select="DatiGenerali/DatiTrasporto/DatiAnagraficiVettore/Anagrafica/Denominazione"/>
		      			<xsl:if test="DatiGenerali/DatiTrasporto/DataOraConsegna">
		      				consegnato il
							<xsl:call-template name="FormatDate">
								<xsl:with-param name="DateTime" select="DatiGenerali/DatiTrasporto/DataOraConsegna" />
							</xsl:call-template>
		      			</xsl:if>
			        </div>
			      </div>
		      </xsl:if>
		      <xsl:if test="DatiPagamento/DettaglioPagamento/DataScadenzaPagamento">
		      	<br/>
			      <div id="notices">
			        <h4>Data Scadenza:</h4>
			        <div class="notice">
		      				
							<xsl:call-template name="FormatDate">
								<xsl:with-param name="DateTime" select="DatiPagamento/DettaglioPagamento/DataScadenzaPagamento" />
							</xsl:call-template>
			        </div>
			      </div>
		      </xsl:if>

	</xsl:template>


<xsl:template name="FormatDate">
		<xsl:param name="DateTime" />

		<xsl:variable name="year" select="substring($DateTime,1,4)" />
		<xsl:variable name="month" select="substring($DateTime,6,2)" />
		<xsl:variable name="day" select="substring($DateTime,9,2)" />

		<xsl:value-of select="$day" />
		<xsl:value-of select="' '" />
		<xsl:choose>
			<xsl:when test="$month = '1' or $month = '01'">
				Gennaio
			</xsl:when>
			<xsl:when test="$month = '2' or $month = '02'">
				Febbraio
			</xsl:when>
			<xsl:when test="$month = '3' or $month = '03'">
				Marzo
			</xsl:when>
			<xsl:when test="$month = '4' or $month = '04'">
				Aprile
			</xsl:when>
			<xsl:when test="$month = '5' or $month = '05'">
				Maggio
			</xsl:when>
			<xsl:when test="$month = '6' or $month = '06'">
				Giugno
			</xsl:when>
			<xsl:when test="$month = '7' or $month = '07'">
				Luglio
			</xsl:when>
			<xsl:when test="$month = '8' or $month = '08'">
				Agosto
			</xsl:when>
			<xsl:when test="$month = '9' or $month = '09'">
				Settembre
			</xsl:when>
			<xsl:when test="$month = '10'">
				Ottobre
			</xsl:when>
			<xsl:when test="$month = '11'">
				Novembre
			</xsl:when>
			<xsl:when test="$month = '12'">
				Dicembre
			</xsl:when>
			<xsl:otherwise>
				Mese non riconosciuto
			</xsl:otherwise>
		</xsl:choose>
		<xsl:value-of select="' '" />
		<xsl:value-of select="$year" />

		<xsl:variable name="time" select="substring($DateTime,12)" />
		<xsl:if test="$time != ''">
			<xsl:variable name="hh" select="substring($time,1,2)" />
			<xsl:variable name="mm" select="substring($time,4,2)" />
			<xsl:variable name="ss" select="substring($time,7,2)" />

			<xsl:value-of select="' '" />
			<xsl:value-of select="$hh" />
			<xsl:value-of select="':'" />
			<xsl:value-of select="$mm" />
			<xsl:value-of select="':'" />
			<xsl:value-of select="$ss" />
		</xsl:if>
	</xsl:template>


</xsl:stylesheet>

