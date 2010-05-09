<html>
<body>
    <div class="content">
            <h2>Supported Syntax</h2>
            <p>
                The following is a list showing supported Textile syntax features.
                Generally unsupported syntax features are silently ignored
                and the unsupported syntax is passed through verbatim to the output.
            </p>
            <h3>Text Styles</h3>
            <ol>
                <li><em>_emphasis_</em></li>
                <li><strong>*strong*</strong></li>
                <li><i>__italic__</i></li>
                <li><b>**bold**</b></li>
                <!--li><cite>??citation??</cite></li-->
                <li><del>-deleted text-</del></li>
                <li><ins>+inserted text+</ins></li>
                <!--li><sup>^superscript^</sup></li>
                <li><sub>~subscript~</sub></li-->
                <li><span>%span%</span></li>
                <li><code>@code@</code></li>
            </ol>
            <h3>Lists, Tables, Quotes & Paragraphs</h3>
            <ol>
                    <li><b>p.</b> Paragraph or a blank line between.</li>
                    <li><b>hn.</b> heading e.g. <b>h1.</b> Is heading one, please put an empty line following.</li>
                    <li><b>bq.</b> Blockquote</li>
                    <li><b>bc.</b> Block code</li>
                    <li><b>pre.</b> Pre-formatted</li>
                    <li><b>#</b> Numeric list</li>
                    <li><b>*</b> Bulleted list</li>
                    <!--li><b>fnn.</b> Footnote</li>
                    <li>reference[1]<br/>fn1. footnote text</li-->
            </ol>
            <h3>Images & Objects</h3>
            <ol>
                    <li>!imageName! </li>
            </ol>
            <h3>Extras</h3>
            <ol>
                    <!--tr>
                        <th colspan="3" align="left">Links</th>
                    </tr>
                    <li>"title":http://www.foo-bar.com</li>
                    <li>!imageUrl!:http://www.foo-bar.com</td></tr-->
                    <li>"quotes" to &#8220;quotes&#8221;</li>
                    <li>'quotes' to &#8216;quotes&#8217;</li>
                    <li>it's to it&#8217;s</li>
                    <li>em -- dash to em &#8212; dash</li>
                    <li>en - dash to en &#8211; dash</li>
                    <li>2 x 4 to 2 &#215; 4</li>
                    <li>foo(tm) to foo&#8482;</li>
                    <li>foo(r) to foo&#174;</li>
                    <li>foo(c) to foo&#169;</li>
                </ol>
                <h3>Advanced</h3>
                <ol>
                    <li>(class)</li>
                    <li>(#id)</li>
                    <li>{style}</li>
                    <li>[language]</li>
                    <!--tr>
                        <th colspan="3" align="left">Alignment</th>
                    </tr>
                    <li>&lt; left</li>
                    <li>&gt; right</li>
                    <li>&lt;&gt; justify</li>
                    <li>= center</li>
                    <tr>
                        <th colspan="3" align="left">Padding</th>
                    </tr>
                    <li>( pad left</li>
                    <li>) pad right</li>
                    <li>() pad left+right</td></tr-->
                    <li><code>|_. a|_. table|_. header|<br/>
    |a|table|row|<br/>
    |a|table|row|</code>
                    </li>
                    <!--tr>
                        <th colspan="3" align="left">Images</th>
                    </tr>
                    <li>!imageUrl!</td></tr-->
                    <li>ABW(A Better Way)</li>
                    <!--tr>
                        <th colspan="3" align="left">Raw HTML</th>
                    </tr>
                    <li></li>
                    <tr>
                        <th colspan="3" align="left">Extended blocks</th>
                    </tr>
                    <li>bc..</li>
                    <li>bq..</li>
                    <tr>
                        <th colspan="3" align="left">Escaped Markup</th>
                    </tr>
                    <li>==no &lt;b&gt;textile&lt;/b&gt; here==</li>
                    <tr>
                        <th colspan="3" align="left">Generated Content</th>
                    </tr>
                    <li>{toc}</li>
                    <li>&nbsp;</td><td colspan="2">
                        Table Of Contents<br/>
                        options include style (any CSS style) and maxLevel.  Eg: {toc:style=disc|maxLevel=2}
                        </li>
                    <li>{glossary}</li>
                    <li>&nbsp;</td><td colspan="2">
                        Glossary<br/>
                        options: style (any CSS style)   Eg: {glossary:style=disc}
                        </td></tr-->
            </ol>
        </div>
</html>
