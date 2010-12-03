myTextileSettings = {
    nameSpace:           "textile", // Useful to prevent multi-instances CSS conflict
    onShiftEnter:        {keepDefault:false, replaceWith:'\n\n'},
    markupSet: [
        {name:'Heading 1', key:'1', closeWith:'\n', openWith:'h1(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Heading 2', key:'2', openWith:'h2(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Heading 3', key:'3', openWith:'h3(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Heading 4', key:'4', openWith:'h4(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Heading 5', key:'5', openWith:'h5(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Heading 6', key:'6', openWith:'h6(!(([![Class]!]))!). ', placeHolder:'Your title here...' },
        {name:'Paragraph', key:'P', openWith:'\np(!(([![Class]!]))!). '},
        {separator:'---------------' },
        {name:'Bold', key:'B', closeWith:'*', openWith:'*'},
        {name:'Italic', key:'I', closeWith:'_', openWith:'_'},
        {name:'Stroke through', key:'S', closeWith:'-', openWith:'-'},
        {separator:'---------------' },
        {name:'Colors', openWith:'%{color:?}', closeWith:'%', dropMenu: [
            {name:'Yellow', openWith:'%{color:yellow}', closeWith:'%', className:"col1-1" },
            {name:'Orange', openWith:'%{color:orange}', closeWith:'%', className:"col1-2" },
            {name:'Red', openWith:'%{color:red}', closeWith:'%', className:"col1-3" },
            {name:'Blue', openWith:'%{color:blue}', closeWith:'%', className:"col2-1" },
            {name:'Purple', openWith:'%{color:purple}', closeWith:'%', className:"col2-2" },
            {name:'Green', openWith:'%{color:green}', closeWith:'%', className:"col2-3" },
            {name:'White', openWith:'%{color:white}', closeWith:'%', className:"col3-1" },
            {name:'Gray', openWith:'%{color:gray}', closeWith:'%', className:"col3-2" },
            {name:'Black', openWith:'%{color:black}', closeWith:'%', className:"col3-3" }
        ]},
        {name:'Size', openWith:'%{font-size:1em}', closeWith:'%', dropMenu :[
            {name:'Big', openWith:'%{font-size:x-large}', closeWith:'%' },
            {name:'Normal', openWith:'%{font-size:1em}', closeWith:'%' },
            {name:'Small', openWith:'%{font-size:x-small}', closeWith:'%' }
        ]},
        {separator:'---------------' },
        {name:'Bulleted list', openWith:'(!(* |!|*)!)'},
        {name:'Numeric list', openWith:'(!(# |!|#)!)'},
        {separator:'---------------' },
        {name:'Internal Picture', replaceWith:'[[![Image Name:!:Logo]!],[![Position:!:left or right]!],60px,60px](image)'},
        {name:'External Picture', replaceWith:'![![Source:!:http://]!]([![Alternative text]!])!'},
        {name:'Insert Link to You Tube Video', replaceWith:'[[![You Tube Video ID (including underscore):!:]!]](youtube)' },
        {name:'Insert Link to Downloadable File', replaceWith:'[[![File name listed under Media, Files:!:a.pdf]!]](file)' },
        {name:'Link', openWith:'"', closeWith:'([![Title]!])":[![Link:!:http://]!]', placeHolder:'Your text to link here...' },
        {separator:'---------------' },
        {name:'Quotes', openWith:'bq(!(([![Class]!]))!). '},
        {name:'Code', openWith:'@', closeWith:'@'},
    ]
}

