<html>
<head>
  <title>Kagyu Samye Dzong London: About Textile</title>
  <meta name="layout" content="main">
</head>
<body>
    <h1>Textile Syntax</h1>
            <p>
            Textile-J provides support for <a href="http://en.wikipedia.org/wiki/Textile_(markup_language)">Textile Markup</a>.
            Textile was the first markup language supported by Textile-J, and thus is the most mature and best supported.
            </p>
            <h2>Supported Syntax</h2>
            <p>
                The following is a table showing supported Textile syntax features for both the Textile parser
                and the JFace viewer.
            </p>
            <p>
                Generally the Textile parser silently ignores unsupported syntax features
                and the unsupported syntax is passed through verbatim to the output.  The JFace viewer also silently
                ignores unrecognized markup and renders the text (sans HTML tags) verbatim to the output.
            </p>
            <table>
                <thead>
                    <tr>
                        <th></th><th>Parser</th><th>JFace Viewer</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th colspan="3" align="left">Phrase modifiers</th>
                    </tr>
                    <tr><td><em>_emphasis_</em></td><td>supported</td><td>supported</td></tr>
                    <tr><td><strong>*strong*</strong></td><td>supported</td><td>supported</td></tr>
                    <tr><td><i>__italic__</i></td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>**bold**</b></td><td>supported</td><td>supported</td></tr>
                    <tr><td><cite>??citation??</cite></td><td>supported</td><td>supported</td></tr>
                    <tr><td><del>-deleted text-</del></td><td>supported</td><td>supported</td></tr>
                    <tr><td><ins>+inserted text+</ins></td><td>supported</td><td>supported</td></tr>
                    <tr><td><sup>^superscript^</sup></td><td>supported</td><td>supported</td></tr>
                    <tr><td><sub>~subscript~</sub></td><td>supported</td><td>supported</td></tr>
                    <tr><td><span>%span%</span></td><td>supported</td><td>supported</td></tr>
                    <tr><td><code>@code@</code></td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Block modifiers</th>
                    </tr>
                    <tr><td><b>hn.</b> heading</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>bq.</b> Blockquote</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>fnn.</b> Footnote</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>p.</b> Paragraph</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>bc.</b> Block code</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>pre.</b> Pre-formatted</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>#</b> Numeric list</td><td>supported</td><td>supported</td></tr>
                    <tr><td><b>*</b> Bulleted list</td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Links</th>
                    </tr>
                    <tr><td>"title":http://www.foo-bar.com</td><td>supported</td><td>supported</td></tr>
                    <tr><td>!imageUrl!:http://www.foo-bar.com</td><td>supported</td><td>not supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Punctuation</th>
                    </tr>
                    <tr><td>"quotes" to &#8220;quotes&#8221;</td><td>supported</td><td>supported</td></tr>
                    <tr><td>'quotes' to &#8216;quotes&#8217;</td><td>supported</td><td>supported</td></tr>
                    <tr><td>it's to it&#8217;s</td><td>supported</td><td>supported</td></tr>
                    <tr><td>em -- dash to em &#8212; dash</td><td>supported</td><td>supported</td></tr>
                    <tr><td>en - dash to en &#8211; dash</td><td>supported</td><td>supported</td></tr>
                    <tr><td>2 x 4 to 2 &#215; 4</td><td>supported</td><td>supported</td></tr>
                    <tr><td>foo(tm) to foo&#8482;</td><td>supported</td><td>supported</td></tr>
                    <tr><td>foo(r) to foo&#174;</td><td>supported</td><td>supported</td></tr>
                    <tr><td>foo(c) to foo&#169;</td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Attributes</th>
                    </tr>
                    <tr><td>(class)</td><td>supported</td><td>supported</td></tr>
                    <tr><td>(#id)</td><td>supported</td><td>supported</td></tr>
                    <tr><td>{style}</td><td>supported</td><td>supported</td></tr>
                    <tr><td>[language]</td><td>supported</td><td>n/a</td></tr>
                    <tr>
                        <th colspan="3" align="left">Alignment</th>
                    </tr>
                    <tr><td>&lt; left</td><td>supported</td><td>not supported</td></tr>
                    <tr><td>&gt; right</td><td>supported</td><td>not supported</td></tr>
                    <tr><td>&lt;&gt; justify</td><td>supported</td><td>not supported</td></tr>
                    <tr><td>= center</td><td>supported</td><td>not supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Padding</th>
                    </tr>
                    <tr><td>( pad left</td><td>supported</td><td>not supported</td></tr>
                    <tr><td>) pad right</td><td>supported</td><td>not supported</td></tr>
                    <tr><td>() pad left+right</td><td>supported</td><td>not supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Tables</th>
                    </tr>
                    <tr><td><code>|_. a|_. table|_. header|<br/>
    |a|table|row|<br/>
    |a|table|row|</code>
                    </td><td>supported</td><td>partial support</td></tr>
                    <tr>
                        <th colspan="3" align="left">Images</th>
                    </tr>
                    <tr><td>!imageUrl!</td><td>supported</td><td>not supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Acronyms</th>
                    </tr>
                    <tr><td>ABW(A Better Way)</td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Footnotes</th>
                    </tr>
                    <tr><td>reference[1]<br/>fn1. footnote text</td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Raw HTML</th>
                    </tr>
                    <tr><td></td><td>supported</td><td>partial support</td></tr>
                    <tr>
                        <th colspan="3" align="left">Extended blocks</th>
                    </tr>
                    <tr><td>bc..</td><td>supported</td><td>supported</td></tr>
                    <tr><td>bq..</td><td>supported</td><td>supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Escaped Markup</th>
                    </tr>
                    <tr><td>==no &lt;b&gt;textile&lt;/b&gt; here==</td><td>supported</td><td>not supported</td></tr>
                    <tr>
                        <th colspan="3" align="left">Generated Content</th>
                    </tr>
                    <tr><td>{toc}</td><td>supported</td><td>supported</td></tr>
                    <tr><td>&nbsp;</td><td colspan="2">
                        Table Of Contents<br/>
                        options include style (any CSS style) and maxLevel.  Eg: {toc:style=disc|maxLevel=2}
                        </td></tr>
                    <tr><td>{glossary}</td><td>supported</td><td>supported</td></tr>
                    <tr><td>&nbsp;</td><td colspan="2">
                        Glossary<br/>
                        options: style (any CSS style)   Eg: {glossary:style=disc}
                        </td></tr>
                </tbody>

            </table>
</body>
</html>
