%{------------------------------------------------------------------------------
- Copyright © 2010 Leanne Northrop
-
- This file is part of Samye Content Management System.
    -
- Samye Content Management System is free software: you can redistribute it
- and/or modify it under the terms of the GNU General Public License as
- published by the Free Software Foundation, either version 3 of the License,
- or (at your option) any later version.
    -
- Samye Content Management System is distributed in the hope that it will be
- useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
- GNU General Public License for more details.
    -
- You should have received a copy of the GNU General Public License
- along with Samye Content Management System.
    - If not, see <http://www.gnu.org/licenses/>.
-
- BT plc, hereby disclaims all copyright interest in the program
- “Samye Content Management System” written by Leanne Northrop.
    ----------------------------------------------------------------------------}%
<html>
  <body>
  <g:set var="theExample">h2{color:green}. This is a title

    h3. This is a subhead

    This is some text of *dubious character*. Isn't the use of "quotes"
    just _lazy_ writing -- and theft of 'intellectual property' besides?</g:set>
  <div class="content">
    <h1>How do I format text?</h1>
    <p>If you would like to make your writing more colourful or formatted in a special way it is possible to do this by inserting  characters with special meaning when you are writing content. For example if you would like to do this:</p>
    <div style="border:1px solid black">
${theExample.encodeAsTextile()}
    </div><br/>
    <p>Then you would type:
    <pre style="border:1px solid black">${theExample}</pre>
    Please note the blank lines are important.
  </p>
  <h1>Supported Formatting Options</h1>
  <p>
    Below is a list of the special characters that are available.
    It is a list showing supported <a href="http://www.textism.com/tools/textile/index.php">Textile</a> syntax features.
    Generally unsupported syntax features are silently ignored
    and the unsupported syntax is passed through verbatim to the output.
  </p>
  <h3>Text Styles</h3>
  <table>
    <tr>
      <th>Special Characters</th><th>What it will look like</th>
    </tr>
    <g:each status="i" in="${['_emphasis_','*strong*','__italic__','**bold**','-overscore-','some %{color:blue}coloured% text','@programming code@']}" var="item">
<tr>
<td>${item}</td>
            <td>${item.encodeAsTextile()}</td>
            </tr>
            </g:each>
            </table>
            <h3>Headings, Lists, Quotes & Paragraphs</h3>
            <table>
            <tr>
            <th>Special Characters</th><th>What it will look like</th>
            </tr>
            <g:set var="listeg1"># A numbered list<br/>
      # With multiple levels<br/>
      ## one<br/>
      ## two<br/>
      ## three<br/>
      ### even lower<br/>
      ## four<br/>
      # five<br/></g:set>
      <g:set var="listeg2">* A numbered list<br/>
        * With multiple levels<br/>
        ** one<br/>
        ** two<br/>
        ** three<br/>
        *** even lower<br/>
        ** four<br/>
        * five<br/></g:set>
      <g:each status="i" in="${['h1. Heading One','h2. Heading Two','h3. Heading Three','h4. Heading Four','h5. Heading Five','h6. Heading Six', 'h2{color:green}. A Green Heading at Level 2',listeg1,listeg2,'bq. Will make this text a block quote.','pre. Uses HTML pre tag around this text.']}" var="item">
        <tr>
          <td>${item}</td>
          <td>${item.encodeAsTextile()}</td>
        </tr>
      </g:each>
  </table>
  <h3>Images & Links</h3>
  <g:set var="externalLink">Display link to "BBC":http://www.bbc.co.uk</g:set>
  <table>
    <tr>
      <th>Special Characters</th><th>What it will look like</th>
    </tr>
    <g:each status="i" in="${['[Logo](image)','[Logo,left](image)','[Logo,right](image)','[Logo,center](image)','[Logo,left,30px,30px](image)','!http://www.samyeling.org/pix/sela7h.jpg!','[File Name](video)','Display link to [17th Gyalwa Karmapa](teacher) page on this site',externalLink]}" var="item">
      <tr>
        <td>${item}</td>
        <td>${item.encodeAsTextile()}</td>
      </tr>
    </g:each>
  </table>
  <h3>Extras</h3>
  <table>
    <tr>
      <th>Special Characters</th><th>What it will look like</th>
    </tr>
    <g:each status="i" in="${['long (em) -- dash','short (en) - dash','the trademark symbol is (tm)','the rights resevered symbol is (r)','copyright symbol (c)','hover mouse over ABW(A Better Way) to see tooltip']}" var="item">
      <tr>
        <td>${item}</td>
        <td>${item.encodeAsTextile()}</td>
      </tr>
    </g:each>
  </table>
  <h3>Tables</h3>
  <p>If you would like to add a table use the following format:</p>
  <p style="font-size:1.5em">
    |_. column heading 1 |_. column heading 2 |_. column heading 3|<br/>
    | first row column 1 value | first row column 2 value | first row column 3 value |<br/>
    | a | table | row |<br/>
  </p>
${'''|_. column heading 1 |_. column heading 2 |_. column heading 3|
| first row column 1 value | first row column 2 value | first row column 3 value |
|a|table|row|'''.encodeAsTextile()}                
</div>
</html>
