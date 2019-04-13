(ns trendcat.db
  (:require [reagent.core :as r]))


(defn set-item!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (.setItem (.-localStorage js/window) key (.stringify js/JSON (clj->js val))))


(defn get-item
  "Returns value of `key' from browser's localStorage."
  [key]
  (js->clj (.parse js/JSON (.getItem (.-localStorage js/window) key)) :keywordize-keys true))


(defonce github-api-url "https://github-trending-api.now.sh")


(defonce hnews-api-url "https://hacker-news.firebaseio.com/v0")


(defonce app-state (r/atom {:github-trends (or (get-item "trendcat-github") [])
                            :hnews-story-items (or (get-item "trendcat-hnews") [])
                            :request-delay (or (get-item "request-delay") 1)
                            :splash-screen (or (get-item "splash-screen") false)
                            :dark-mode (or (get-item "dark-mode") false)
                            :stories (or (get-item "stories") "topstories")
                            :lang (or (get-item "current-lang") nil)
                            :since (or (get-item "current-since") "daily")
                            :delay-options [{:value 1 :label "Every Time"}
                                           {:value 60 :label "1 Minute"}
                                           {:value 300 :label "5 Minutes"}
                                           {:value 600 :label "10 Minutes"}
                                           {:value 900 :label "15 Minutes"}
                                           {:value 1800 :label "30 Minutes"}]
                            :fav-languages [{:urlParam "actionscript" :name "ActionScript"}
                                            {:urlParam "c" :name "C"}
                                            {:urlParam "c%23" :name "C#"}
                                            {:urlParam "c++" :name "C++"}
                                            {:urlParam "clojure" :name "Clojure"}
                                            {:urlParam "css" :name "CSS"}
                                            {:urlParam "go" :name "Go"}
                                            {:urlParam "haskell" :name "Haskell"}
                                            {:urlParam "html" :name "HTML"}
                                            {:urlParam "java" :name "Java"}
                                            {:urlParam "javascript" :name "JavaScript"}
                                            {:urlParam "lua" :name "Lua"}
                                            {:urlParam "matlab" :name "Matlab"}
                                            {:urlParam "objective-c" :name "Objective-C"}
                                            {:urlParam "php" :name "PHP"}
                                            {:urlParam "python" :name "Python"}
                                            {:urlParam "r" :name "R"}
                                            {:urlParam "ruby" :name "Ruby"}
                                            {:urlParam "scala" :name "Scala"}
                                            {:urlParam "shell" :name "Shell"}
                                            {:urlParam "swift" :name "Swift"}
                                            {:urlParam "tex" :name "TeX"}
                                            {:urlParam "typescript" :name "TypeScript"}
                                            {:urlParam "vim-script" :name "Vim script"}]
                            :languages [{:urlParam "1c-enterprise" :name "1C Enterprise"}
                                        {:urlParam "abap" :name "ABAP"}
                                        {:urlParam "abnf" :name "ABNF"}
                                        {:urlParam "ada" :name "Ada"}
                                        {:urlParam "adobe-font-metrics" :name "Adobe Font Metrics"}
                                        {:urlParam "agda" :name "Agda"}
                                        {:urlParam "ags-script" :name "AGS Script"}
                                        {:urlParam "alloy" :name "Alloy"}
                                        {:urlParam "alpine-abuild" :name "Alpine Abuild"}
                                        {:urlParam "ampl" :name "AMPL"}
                                        {:urlParam "angelscript" :name "AngelScript"}
                                        {:urlParam "ant-build-system" :name "Ant Build System"}
                                        {:urlParam "antlr" :name "ANTLR"}
                                        {:urlParam "apacheconf" :name "ApacheConf"}
                                        {:urlParam "apex" :name "Apex"}
                                        {:urlParam "api-blueprint" :name "API Blueprint"}
                                        {:urlParam "apl" :name "APL"}
                                        {:urlParam "apollo-guidance-computer" :name "Apollo Guidance Computer"}
                                        {:urlParam "applescript" :name "AppleScript"}
                                        {:urlParam "arc" :name "Arc"}
                                        {:urlParam "asciidoc" :name "AsciiDoc"}
                                        {:urlParam "asn.1" :name "ASN.1"}
                                        {:urlParam "asp" :name "ASP"}
                                        {:urlParam "aspectj" :name "AspectJ"}
                                        {:urlParam "assembly" :name "Assembly"}
                                        {:urlParam "ats" :name "ATS"}
                                        {:urlParam "augeas" :name "Augeas"}
                                        {:urlParam "autohotkey" :name "AutoHotkey"}
                                        {:urlParam "autoit" :name "AutoIt"}
                                        {:urlParam "awk" :name "Awk"}
                                        {:urlParam "ballerina" :name "Ballerina"}
                                        {:urlParam "batchfile" :name "Batchfile"}
                                        {:urlParam "befunge" :name "Befunge"}
                                        {:urlParam "bison" :name "Bison"}
                                        {:urlParam "bitbake" :name "BitBake"}
                                        {:urlParam "blade" :name "Blade"}
                                        {:urlParam "blitzbasic" :name "BlitzBasic"}
                                        {:urlParam "blitzmax" :name "BlitzMax"}
                                        {:urlParam "bluespec" :name "Bluespec"}
                                        {:urlParam "boo" :name "Boo"}
                                        {:urlParam "brainfuck" :name "Brainfuck"}
                                        {:urlParam "brightscript" :name "Brightscript"}
                                        {:urlParam "bro" :name "Bro"}
                                        {:urlParam "c-objdump" :name "C-ObjDump"}
                                        {:urlParam "c2hs-haskell" :name "C2hs Haskell"}
                                        {:urlParam "cap'n-proto" :name "Cap'n Proto"}
                                        {:urlParam "cartocss" :name "CartoCSS"}
                                        {:urlParam "ceylon" :name "Ceylon"}
                                        {:urlParam "chapel" :name "Chapel"}
                                        {:urlParam "charity" :name "Charity"}
                                        {:urlParam "chuck" :name "ChucK"}
                                        {:urlParam "cirru" :name "Cirru"}
                                        {:urlParam "clarion" :name "Clarion"}
                                        {:urlParam "clean" :name "Clean"}
                                        {:urlParam "click" :name "Click"}
                                        {:urlParam "clips" :name "CLIPS"}
                                        {:urlParam "closure-templates" :name "Closure Templates"}
                                        {:urlParam "cmake" :name "CMake"}
                                        {:urlParam "cobol" :name "COBOL"}
                                        {:urlParam "coffeescript" :name "CoffeeScript"}
                                        {:urlParam "coldfusion" :name "ColdFusion"}
                                        {:urlParam "coldfusion-cfc" :name "ColdFusion CFC"}
                                        {:urlParam "collada" :name "COLLADA"}
                                        {:urlParam "common-lisp" :name "Common Lisp"}
                                        {:urlParam "common-workflow-language" :name "Common Workflow Language"}
                                        {:urlParam "component-pascal" :name "Component Pascal"}
                                        {:urlParam "cool" :name "Cool"}
                                        {:urlParam "coq" :name "Coq"}
                                        {:urlParam "cpp-objdump" :name "Cpp-ObjDump"}
                                        {:urlParam "creole" :name "Creole"}
                                        {:urlParam "crystal" :name "Crystal"}
                                        {:urlParam "cson" :name "CSON"}
                                        {:urlParam "csound" :name "Csound"}
                                        {:urlParam "csound-document" :name "Csound Document"}
                                        {:urlParam "csound-score" :name "Csound Score"}
                                        {:urlParam "csv" :name "CSV"}
                                        {:urlParam "cuda" :name "Cuda"}
                                        {:urlParam "cweb" :name "CWeb"}
                                        {:urlParam "cycript" :name "Cycript"}
                                        {:urlParam "cython" :name "Cython"}
                                        {:urlParam "d" :name "D"}
                                        {:urlParam "d-objdump" :name "D-ObjDump"}
                                        {:urlParam "darcs-patch" :name "Darcs Patch"}
                                        {:urlParam "dart" :name "Dart"}
                                        {:urlParam "dataweave" :name "DataWeave"}
                                        {:urlParam "desktop" :name "desktop"}
                                        {:urlParam "diff" :name "Diff"}
                                        {:urlParam "digital-command-language" :name "DIGITAL Command Language"}
                                        {:urlParam "dm" :name "DM"}
                                        {:urlParam "dns-zone" :name "DNS Zone"}
                                        {:urlParam "dockerfile" :name "Dockerfile"}
                                        {:urlParam "dogescript" :name "Dogescript"}
                                        {:urlParam "dtrace" :name "DTrace"}
                                        {:urlParam "dylan" :name "Dylan"}
                                        {:urlParam "e" :name "E"}
                                        {:urlParam "eagle" :name "Eagle"}
                                        {:urlParam "easybuild" :name "Easybuild"}
                                        {:urlParam "ebnf" :name "EBNF"}
                                        {:urlParam "ec" :name "eC"}
                                        {:urlParam "ecere-projects" :name "Ecere Projects"}
                                        {:urlParam "ecl" :name "ECL"}
                                        {:urlParam "eclipse" :name "ECLiPSe"}
                                        {:urlParam "edje-data-collection" :name "Edje Data Collection"}
                                        {:urlParam "edn" :name "edn"}
                                        {:urlParam "eiffel" :name "Eiffel"}
                                        {:urlParam "ejs" :name "EJS"}
                                        {:urlParam "elixir" :name "Elixir"}
                                        {:urlParam "elm" :name "Elm"}
                                        {:urlParam "emacs-lisp" :name "Emacs Lisp"}
                                        {:urlParam "emberscript" :name "EmberScript"}
                                        {:urlParam "eq" :name "EQ"}
                                        {:urlParam "erlang" :name "Erlang"}
                                        {:urlParam "f%23" :name "F#"}
                                        {:urlParam "factor" :name "Factor"}
                                        {:urlParam "fancy" :name "Fancy"}
                                        {:urlParam "fantom" :name "Fantom"}
                                        {:urlParam "filebench-wml" :name "Filebench WML"}
                                        {:urlParam "filterscript" :name "Filterscript"}
                                        {:urlParam "fish" :name "fish"}
                                        {:urlParam "flux" :name "FLUX"}
                                        {:urlParam "formatted" :name "Formatted"}
                                        {:urlParam "forth" :name "Forth"}
                                        {:urlParam "fortran" :name "Fortran"}
                                        {:urlParam "freemarker" :name "FreeMarker"}
                                        {:urlParam "frege" :name "Frege"}
                                        {:urlParam "g-code" :name "G-code"}
                                        {:urlParam "game-maker-language" :name "Game Maker Language"}
                                        {:urlParam "gams" :name "GAMS"}
                                        {:urlParam "gap" :name "GAP"}
                                        {:urlParam "gcc-machine-description" :name "GCC Machine Description"}
                                        {:urlParam "gdb" :name "GDB"}
                                        {:urlParam "gdscript" :name "GDScript"}
                                        {:urlParam "genie" :name "Genie"}
                                        {:urlParam "genshi" :name "Genshi"}
                                        {:urlParam "gentoo-ebuild" :name "Gentoo Ebuild"}
                                        {:urlParam "gentoo-eclass" :name "Gentoo Eclass"}
                                        {:urlParam "gerber-image" :name "Gerber Image"}
                                        {:urlParam "gettext-catalog" :name "Gettext Catalog"}
                                        {:urlParam "gherkin" :name "Gherkin"}
                                        {:urlParam "glsl" :name "GLSL"}
                                        {:urlParam "glyph" :name "Glyph"}
                                        {:urlParam "gn" :name "GN"}
                                        {:urlParam "gnuplot" :name "Gnuplot"}
                                        {:urlParam "golo" :name "Golo"}
                                        {:urlParam "gosu" :name "Gosu"}
                                        {:urlParam "grace" :name "Grace"}
                                        {:urlParam "gradle" :name "Gradle"}
                                        {:urlParam "grammatical-framework" :name "Grammatical Framework"}
                                        {:urlParam "graph-modeling-language" :name "Graph Modeling Language"}
                                        {:urlParam "graphql" :name "GraphQL"}
                                        {:urlParam "graphviz-(dot)" :name "Graphviz (DOT)"}
                                        {:urlParam "groovy" :name "Groovy"}
                                        {:urlParam "groovy-server-pages" :name "Groovy Server Pages"}
                                        {:urlParam "hack" :name "Hack"}
                                        {:urlParam "haml" :name "Haml"}
                                        {:urlParam "handlebars" :name "Handlebars"}
                                        {:urlParam "harbour" :name "Harbour"}
                                        {:urlParam "haxe" :name "Haxe"}
                                        {:urlParam "hcl" :name "HCL"}
                                        {:urlParam "hlsl" :name "HLSL"}
                                        {:urlParam "html+django" :name "HTML+Django"}
                                        {:urlParam "html+ecr" :name "HTML+ECR"}
                                        {:urlParam "html+eex" :name "HTML+EEX"}
                                        {:urlParam "html+erb" :name "HTML+ERB"}
                                        {:urlParam "html+php" :name "HTML+PHP"}
                                        {:urlParam "http" :name "HTTP"}
                                        {:urlParam "hy" :name "Hy"}
                                        {:urlParam "hyphy" :name "HyPhy"}
                                        {:urlParam "idl" :name "IDL"}
                                        {:urlParam "idris" :name "Idris"}
                                        {:urlParam "igor-pro" :name "IGOR Pro"}
                                        {:urlParam "inform-7" :name "Inform 7"}
                                        {:urlParam "ini" :name "INI"}
                                        {:urlParam "inno-setup" :name "Inno Setup"}
                                        {:urlParam "io" :name "Io"}
                                        {:urlParam "ioke" :name "Ioke"}
                                        {:urlParam "irc-log" :name "IRC log"}
                                        {:urlParam "isabelle" :name "Isabelle"}
                                        {:urlParam "isabelle-root" :name "Isabelle ROOT"}
                                        {:urlParam "j" :name "J"}
                                        {:urlParam "jasmin" :name "Jasmin"}
                                        {:urlParam "java-server-pages" :name "Java Server Pages"}
                                        {:urlParam "jflex" :name "JFlex"}
                                        {:urlParam "jison" :name "Jison"}
                                        {:urlParam "jison-lex" :name "Jison Lex"}
                                        {:urlParam "jolie" :name "Jolie"}
                                        {:urlParam "json" :name "JSON"}
                                        {:urlParam "json5" :name "JSON5"}
                                        {:urlParam "jsoniq" :name "JSONiq"}
                                        {:urlParam "jsonld" :name "JSONLD"}
                                        {:urlParam "jsx" :name "JSX"}
                                        {:urlParam "julia" :name "Julia"}
                                        {:urlParam "jupyter-notebook" :name "Jupyter Notebook"}
                                        {:urlParam "kicad-layout" :name "KiCad Layout"}
                                        {:urlParam "kicad-legacy-layout" :name "KiCad Legacy Layout"}
                                        {:urlParam "kicad-schematic" :name "KiCad Schematic"}
                                        {:urlParam "kit" :name "Kit"}
                                        {:urlParam "kotlin" :name "Kotlin"}
                                        {:urlParam "krl" :name "KRL"}
                                        {:urlParam "labview" :name "LabVIEW"}
                                        {:urlParam "lasso" :name "Lasso"}
                                        {:urlParam "latte" :name "Latte"}
                                        {:urlParam "lean" :name "Lean"}
                                        {:urlParam "less" :name "Less"}
                                        {:urlParam "lex" :name "Lex"}
                                        {:urlParam "lfe" :name "LFE"}
                                        {:urlParam "lilypond" :name "LilyPond"}
                                        {:urlParam "limbo" :name "Limbo"}
                                        {:urlParam "linker-script" :name "Linker Script"}
                                        {:urlParam "linux-kernel-module" :name "Linux Kernel Module"}
                                        {:urlParam "liquid" :name "Liquid"}
                                        {:urlParam "literate-agda" :name "Literate Agda"}
                                        {:urlParam "literate-coffeescript" :name "Literate CoffeeScript"}
                                        {:urlParam "literate-haskell" :name "Literate Haskell"}
                                        {:urlParam "livescript" :name "LiveScript"}
                                        {:urlParam "llvm" :name "LLVM"}
                                        {:urlParam "logos" :name "Logos"}
                                        {:urlParam "logtalk" :name "Logtalk"}
                                        {:urlParam "lolcode" :name "LOLCODE"}
                                        {:urlParam "lookml" :name "LookML"}
                                        {:urlParam "loomscript" :name "LoomScript"}
                                        {:urlParam "lsl" :name "LSL"}
                                        {:urlParam "m" :name "M"}
                                        {:urlParam "m4" :name "M4"}
                                        {:urlParam "m4sugar" :name "M4Sugar"}
                                        {:urlParam "makefile" :name "Makefile"}
                                        {:urlParam "mako" :name "Mako"}
                                        {:urlParam "markdown" :name "Markdown"}
                                        {:urlParam "marko" :name "Marko"}
                                        {:urlParam "mask" :name "Mask"}
                                        {:urlParam "mathematica" :name "Mathematica"}
                                        {:urlParam "maven-pom" :name "Maven POM"}
                                        {:urlParam "max" :name "Max"}
                                        {:urlParam "maxscript" :name "MAXScript"}
                                        {:urlParam "mediawiki" :name "MediaWiki"}
                                        {:urlParam "mercury" :name "Mercury"}
                                        {:urlParam "meson" :name "Meson"}
                                        {:urlParam "metal" :name "Metal"}
                                        {:urlParam "minid" :name "MiniD"}
                                        {:urlParam "mirah" :name "Mirah"}
                                        {:urlParam "modelica" :name "Modelica"}
                                        {:urlParam "modula-2" :name "Modula-2"}
                                        {:urlParam "module-management-system" :name "Module Management System"}
                                        {:urlParam "monkey" :name "Monkey"}
                                        {:urlParam "moocode" :name "Moocode"}
                                        {:urlParam "moonscript" :name "MoonScript"}
                                        {:urlParam "mql4" :name "MQL4"}
                                        {:urlParam "mql5" :name "MQL5"}
                                        {:urlParam "mtml" :name "MTML"}
                                        {:urlParam "muf" :name "MUF"}
                                        {:urlParam "mupad" :name "mupad"}
                                        {:urlParam "myghty" :name "Myghty"}
                                        {:urlParam "ncl" :name "NCL"}
                                        {:urlParam "nearley" :name "Nearley"}
                                        {:urlParam "nemerle" :name "Nemerle"}
                                        {:urlParam "nesc" :name "nesC"}
                                        {:urlParam "netlinx" :name "NetLinx"}
                                        {:urlParam "netlinx+erb" :name "NetLinx+ERB"}
                                        {:urlParam "netlogo" :name "NetLogo"}
                                        {:urlParam "newlisp" :name "NewLisp"}
                                        {:urlParam "nextflow" :name "Nextflow"}
                                        {:urlParam "nginx" :name "Nginx"}
                                        {:urlParam "nim" :name "Nim"}
                                        {:urlParam "ninja" :name "Ninja"}
                                        {:urlParam "nit" :name "Nit"}
                                        {:urlParam "nix" :name "Nix"}
                                        {:urlParam "nl" :name "NL"}
                                        {:urlParam "nsis" :name "NSIS"}
                                        {:urlParam "nu" :name "Nu"}
                                        {:urlParam "numpy" :name "NumPy"}
                                        {:urlParam "objdump" :name "ObjDump"}
                                        {:urlParam "objective-c++" :name "Objective-C++"}
                                        {:urlParam "objective-j" :name "Objective-J"}
                                        {:urlParam "ocaml" :name "OCaml"}
                                        {:urlParam "omgrofl" :name "Omgrofl"}
                                        {:urlParam "ooc" :name "ooc"}
                                        {:urlParam "opa" :name "Opa"}
                                        {:urlParam "opal" :name "Opal"}
                                        {:urlParam "opencl" :name "OpenCL"}
                                        {:urlParam "openedge-abl" :name "OpenEdge ABL"}
                                        {:urlParam "openrc-runscript" :name "OpenRC runscript"}
                                        {:urlParam "openscad" :name "OpenSCAD"}
                                        {:urlParam "opentype-feature-file" :name "OpenType Feature File"}
                                        {:urlParam "org" :name "Org"}
                                        {:urlParam "ox" :name "Ox"}
                                        {:urlParam "oxygene" :name "Oxygene"}
                                        {:urlParam "oz" :name "Oz"}
                                        {:urlParam "p4" :name "P4"}
                                        {:urlParam "pan" :name "Pan"}
                                        {:urlParam "papyrus" :name "Papyrus"}
                                        {:urlParam "parrot" :name "Parrot"}
                                        {:urlParam "parrot-assembly" :name "Parrot Assembly"}
                                        {:urlParam "parrot-internal-representation" :name "Parrot Internal Representation"}
                                        {:urlParam "pascal" :name "Pascal"}
                                        {:urlParam "pawn" :name "PAWN"}
                                        {:urlParam "pep8" :name "Pep8"}
                                        {:urlParam "perl" :name "Perl"}
                                        {:urlParam "perl-6" :name "Perl 6"}
                                        {:urlParam "pic" :name "Pic"}
                                        {:urlParam "pickle" :name "Pickle"}
                                        {:urlParam "picolisp" :name "PicoLisp"}
                                        {:urlParam "piglatin" :name "PigLatin"}
                                        {:urlParam "pike" :name "Pike"}
                                        {:urlParam "plpgsql" :name "PLpgSQL"}
                                        {:urlParam "plsql" :name "PLSQL"}
                                        {:urlParam "pod" :name "Pod"}
                                        {:urlParam "pogoscript" :name "PogoScript"}
                                        {:urlParam "pony" :name "Pony"}
                                        {:urlParam "postcss" :name "PostCSS"}
                                        {:urlParam "postscript" :name "PostScript"}
                                        {:urlParam "pov-ray-sdl" :name "POV-Ray SDL"}
                                        {:urlParam "powerbuilder" :name "PowerBuilder"}
                                        {:urlParam "powershell" :name "PowerShell"}
                                        {:urlParam "processing" :name "Processing"}
                                        {:urlParam "prolog" :name "Prolog"}
                                        {:urlParam "propeller-spin" :name "Propeller Spin"}
                                        {:urlParam "protocol-buffer" :name "Protocol Buffer"}
                                        {:urlParam "public-key" :name "Public Key"}
                                        {:urlParam "pug" :name "Pug"}
                                        {:urlParam "puppet" :name "Puppet"}
                                        {:urlParam "pure-data" :name "Pure Data"}
                                        {:urlParam "purebasic" :name "PureBasic"}
                                        {:urlParam "purescript" :name "PureScript"}
                                        {:urlParam "python-console" :name "Python console"}
                                        {:urlParam "python-traceback" :name "Python traceback"}
                                        {:urlParam "qmake" :name "QMake"}
                                        {:urlParam "qml" :name "QML"}
                                        {:urlParam "racket" :name "Racket"}
                                        {:urlParam "ragel" :name "Ragel"}
                                        {:urlParam "raml" :name "RAML"}
                                        {:urlParam "rascal" :name "Rascal"}
                                        {:urlParam "raw-token-data" :name "Raw token data"}
                                        {:urlParam "rdoc" :name "RDoc"}
                                        {:urlParam "realbasic" :name "REALbasic"}
                                        {:urlParam "reason" :name "Reason"}
                                        {:urlParam "rebol" :name "Rebol"}
                                        {:urlParam "red" :name "Red"}
                                        {:urlParam "redcode" :name "Redcode"}
                                        {:urlParam "regular-expression" :name "Regular Expression"}
                                        {:urlParam "ren'py" :name "Ren'Py"}
                                        {:urlParam "renderscript" :name "RenderScript"}
                                        {:urlParam "restructuredtext" :name "reStructuredText"}
                                        {:urlParam "rexx" :name "REXX"}
                                        {:urlParam "rhtml" :name "RHTML"}
                                        {:urlParam "ring" :name "Ring"}
                                        {:urlParam "rmarkdown" :name "RMarkdown"}
                                        {:urlParam "robotframework" :name "RobotFramework"}
                                        {:urlParam "roff" :name "Roff"}
                                        {:urlParam "rouge" :name "Rouge"}
                                        {:urlParam "rpc" :name "RPC"}
                                        {:urlParam "rpm-spec" :name "RPM Spec"}
                                        {:urlParam "runoff" :name "RUNOFF"}
                                        {:urlParam "rust" :name "Rust"}
                                        {:urlParam "sage" :name "Sage"}
                                        {:urlParam "saltstack" :name "SaltStack"}
                                        {:urlParam "sas" :name "SAS"}
                                        {:urlParam "sass" :name "Sass"}
                                        {:urlParam "scaml" :name "Scaml"}
                                        {:urlParam "scheme" :name "Scheme"}
                                        {:urlParam "scilab" :name "Scilab"}
                                        {:urlParam "scss" :name "SCSS"}
                                        {:urlParam "sed" :name "sed"}
                                        {:urlParam "self" :name "Self"}
                                        {:urlParam "shaderlab" :name "ShaderLab"}
                                        {:urlParam "shellsession" :name "ShellSession"}
                                        {:urlParam "shen" :name "Shen"}
                                        {:urlParam "slash" :name "Slash"}
                                        {:urlParam "slim" :name "Slim"}
                                        {:urlParam "smali" :name "Smali"}
                                        {:urlParam "smalltalk" :name "Smalltalk"}
                                        {:urlParam "smarty" :name "Smarty"}
                                        {:urlParam "smt" :name "SMT"}
                                        {:urlParam "solidity" :name "Solidity"}
                                        {:urlParam "sourcepawn" :name "SourcePawn"}
                                        {:urlParam "sparql" :name "SPARQL"}
                                        {:urlParam "spline-font-database" :name "Spline Font Database"}
                                        {:urlParam "sqf" :name "SQF"}
                                        {:urlParam "sql" :name "SQL"}
                                        {:urlParam "sqlpl" :name "SQLPL"}
                                        {:urlParam "squirrel" :name "Squirrel"}
                                        {:urlParam "srecode-template" :name "SRecode Template"}
                                        {:urlParam "stan" :name "Stan"}
                                        {:urlParam "standard-ml" :name "Standard ML"}
                                        {:urlParam "stata" :name "Stata"}
                                        {:urlParam "ston" :name "STON"}
                                        {:urlParam "stylus" :name "Stylus"}
                                        {:urlParam "sublime-text-config" :name "Sublime Text Config"}
                                        {:urlParam "subrip-text" :name "SubRip Text"}
                                        {:urlParam "sugarss" :name "SugarSS"}
                                        {:urlParam "supercollider" :name "SuperCollider"}
                                        {:urlParam "svg" :name "SVG"}
                                        {:urlParam "systemverilog" :name "SystemVerilog"}
                                        {:urlParam "tcl" :name "Tcl"}
                                        {:urlParam "tcsh" :name "Tcsh"}
                                        {:urlParam "tea" :name "Tea"}
                                        {:urlParam "terra" :name "Terra"}
                                        {:urlParam "text" :name "Text"}
                                        {:urlParam "textile" :name "Textile"}
                                        {:urlParam "thrift" :name "Thrift"}
                                        {:urlParam "ti-program" :name "TI Program"}
                                        {:urlParam "tla" :name "TLA"}
                                        {:urlParam "toml" :name "TOML"}
                                        {:urlParam "turing" :name "Turing"}
                                        {:urlParam "turtle" :name "Turtle"}
                                        {:urlParam "twig" :name "Twig"}
                                        {:urlParam "txl" :name "TXL"}
                                        {:urlParam "type-language" :name "Type Language"}
                                        {:urlParam "unified-parallel-c" :name "Unified Parallel C"}
                                        {:urlParam "unity3d-asset" :name "Unity3D Asset"}
                                        {:urlParam "unix-assembly" :name "Unix Assembly"}
                                        {:urlParam "uno" :name "Uno"}
                                        {:urlParam "unrealscript" :name "UnrealScript"}
                                        {:urlParam "urweb" :name "UrWeb"}
                                        {:urlParam "vala" :name "Vala"}
                                        {:urlParam "vcl" :name "VCL"}
                                        {:urlParam "verilog" :name "Verilog"}
                                        {:urlParam "vhdl" :name "VHDL"}
                                        {:urlParam "visual-basic" :name "Visual Basic"}
                                        {:urlParam "volt" :name "Volt"}
                                        {:urlParam "vue" :name "Vue"}
                                        {:urlParam "wavefront-material" :name "Wavefront Material"}
                                        {:urlParam "wavefront-object" :name "Wavefront Object"}
                                        {:urlParam "wdl" :name "wdl"}
                                        {:urlParam "web-ontology-language" :name "Web Ontology Language"}
                                        {:urlParam "webassembly" :name "WebAssembly"}
                                        {:urlParam "webidl" :name "WebIDL"}
                                        {:urlParam "wisp" :name "wisp"}
                                        {:urlParam "world-of-warcraft-addon-data" :name "World of Warcraft Addon Data"}
                                        {:urlParam "x10" :name "X10"}
                                        {:urlParam "xbase" :name "xBase"}
                                        {:urlParam "xc" :name "XC"}
                                        {:urlParam "xcompose" :name "XCompose"}
                                        {:urlParam "xml" :name "XML"}
                                        {:urlParam "xojo" :name "Xojo"}
                                        {:urlParam "xpages" :name "XPages"}
                                        {:urlParam "xpm" :name "XPM"}
                                        {:urlParam "xproc" :name "XProc"}
                                        {:urlParam "xquery" :name "XQuery"}
                                        {:urlParam "xs" :name "XS"}
                                        {:urlParam "xslt" :name "XSLT"}
                                        {:urlParam "xtend" :name "Xtend"}
                                        {:urlParam "yacc" :name "Yacc"}
                                        {:urlParam "yaml" :name "YAML"}
                                        {:urlParam "yang" :name "YANG"}
                                        {:urlParam "yara" :name "YARA"}
                                        {:urlParam "zephir" :name "Zephir"}
                                        {:urlParam "zimpl" :name "Zimpl"}]}))


(defonce moon-toggle "data:image/png;base64,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")
