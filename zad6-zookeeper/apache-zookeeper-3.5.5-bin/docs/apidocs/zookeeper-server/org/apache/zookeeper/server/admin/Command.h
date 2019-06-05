<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- NewPage -->
<html lang="en">
<head>
<!-- Generated by javadoc (1.8.0_201) on Fri May 03 05:07:44 PDT 2019 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Command (Apache ZooKeeper - Server 3.5.5 API)</title>
<meta name="date" content="2019-05-03">
<link rel="stylesheet" type="text/css" href="../../../../../stylesheet.css" title="Style">
<script type="text/javascript" src="../../../../../script.js"></script>
</head>
<body>
<script type="text/javascript"><!--
    try {
        if (location.href.indexOf('is-external=true') == -1) {
            parent.document.title="Command (Apache ZooKeeper - Server 3.5.5 API)";
        }
    }
    catch(err) {
    }
//-->
var methods = {"i0":6,"i1":6,"i2":6,"i3":6};
var tabs = {65535:["t0","All Methods"],2:["t2","Instance Methods"],4:["t3","Abstract Methods"]};
var altColor = "altColor";
var rowColor = "rowColor";
var tableTab = "tableTab";
var activeTableTab = "activeTableTab";
</script>
<noscript>
<div>JavaScript is disabled on your browser.</div>
</noscript>
<!-- ========= START OF TOP NAVBAR ======= -->
<div class="topNav"><a name="navbar.top">
<!--   -->
</a>
<div class="skipNav"><a href="#skip.navbar.top" title="Skip navigation links">Skip navigation links</a></div>
<a name="navbar.top.firstrow">
<!--   -->
</a>
<ul class="navList" title="Navigation">
<li><a href="../../../../../overview-summary.html">Overview</a></li>
<li><a href="package-summary.html">Package</a></li>
<li class="navBarCell1Rev">Class</li>
<li><a href="class-use/Command.html">Use</a></li>
<li><a href="package-tree.html">Tree</a></li>
<li><a href="../../../../../deprecated-list.html">Deprecated</a></li>
<li><a href="../../../../../index-all.html">Index</a></li>
<li><a href="../../../../../help-doc.html">Help</a></li>
</ul>
</div>
<div class="subNav">
<ul class="navList">
<li><a href="../../../../../org/apache/zookeeper/server/admin/AdminServerFactory.html" title="class in org.apache.zookeeper.server.admin"><span class="typeNameLink">Prev&nbsp;Class</span></a></li>
<li><a href="../../../../../org/apache/zookeeper/server/admin/CommandBase.html" title="class in org.apache.zookeeper.server.admin"><span class="typeNameLink">Next&nbsp;Class</span></a></li>
</ul>
<ul class="navList">
<li><a href="../../../../../index.html?org/apache/zookeeper/server/admin/Command.html" target="_top">Frames</a></li>
<li><a href="Command.html" target="_top">No&nbsp;Frames</a></li>
</ul>
<ul class="navList" id="allclasses_navbar_top">
<li><a href="../../../../../allclasses-noframe.html">All&nbsp;Classes</a></li>
</ul>
<div>
<script type="text/javascript"><!--
  allClassesLink = document.getElementById("allclasses_navbar_top");
  if(window==top) {
    allClassesLink.style.display = "block";
  }
  else {
    allClassesLink.style.display = "none";
  }
  //-->
</script>
</div>
<div>
<ul class="subNavList">
<li>Summary:&nbsp;</li>
<li>Nested&nbsp;|&nbsp;</li>
<li>Field&nbsp;|&nbsp;</li>
<li>Constr&nbsp;|&nbsp;</li>
<li><a href="#method.summary">Method</a></li>
</ul>
<ul class="subNavList">
<li>Detail:&nbsp;</li>
<li>Field&nbsp;|&nbsp;</li>
<li>Constr&nbsp;|&nbsp;</li>
<li><a href="#method.detail">Method</a></li>
</ul>
</div>
<a name="skip.navbar.top">
<!--   -->
</a></div>
<!-- ========= END OF TOP NAVBAR ========= -->
<!-- ======== START OF CLASS DATA ======== -->
<div class="header">
<div class="subTitle">org.apache.zookeeper.server.admin</div>
<h2 title="Interface Command" class="title">Interface Command</h2>
</div>
<div class="contentContainer">
<div class="description">
<ul class="blockList">
<li class="blockList">
<dl>
<dt>All Known Implementing Classes:</dt>
<dd><a href="../../../../../org/apache/zookeeper/server/admin/CommandBase.html" title="class in org.apache.zookeeper.server.admin">CommandBase</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.CnxnStatResetCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.CnxnStatResetCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.ConfCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.ConfCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.ConsCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.ConsCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.DirsCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.DirsCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.DumpCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.DumpCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.EnvCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.EnvCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.GetTraceMaskCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.GetTraceMaskCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.IsroCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.IsroCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.MonitorCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.MonitorCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.RuokCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.RuokCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.SetTraceMaskCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.SetTraceMaskCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.SrvrCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.SrvrCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.StatCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.StatCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.StatResetCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.StatResetCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.WatchCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.WatchCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.WatchesByPathCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.WatchesByPathCommand</a>, <a href="../../../../../org/apache/zookeeper/server/admin/Commands.WatchSummaryCommand.html" title="class in org.apache.zookeeper.server.admin">Commands.WatchSummaryCommand</a></dd>
</dl>
<hr>
<br>
<pre>public interface <span class="typeNameLabel">Command</span></pre>
<div class="block">Interface implemented by all commands runnable by JettyAdminServer.</div>
<dl>
<dt><span class="seeLabel">See Also:</span></dt>
<dd><a href="../../../../../org/apache/zookeeper/server/admin/CommandBase.html" title="class in org.apache.zookeeper.server.admin"><code>CommandBase</code></a>, 
<a href="../../../../../org/apache/zookeeper/server/admin/Commands.html" title="class in org.apache.zookeeper.server.admin"><code>Commands</code></a>, 
<a href="../../../../../org/apache/zookeeper/server/admin/JettyAdminServer.html" title="class in org.apache.zookeeper.server.admin"><code>JettyAdminServer</code></a></dd>
</dl>
</li>
</ul>
</div>
<div class="summary">
<ul class="blockList">
<li class="blockList">
<!-- ========== METHOD SUMMARY =========== -->
<ul class="blockList">
<li class="blockList"><a name="method.summary">
<!--   -->
</a>
<h3>Method Summary</h3>
<table class="memberSummary" border="0" cellpadding="3" cellspacing="0" summary="Method Summary table, listing methods, and an explanation">
<caption><span id="t0" class="activeTableTab"><span>All Methods</span><span class="tabEnd">&nbsp;</span></span><span id="t2" class="tableTab"><span><a href="javascript:show(2);">Instance Methods</a></span><span class="tabEnd">&nbsp;</span></span><span id="t3" class="tableTab"><span><a href="javascript:show(4);">Abstract Methods</a></span><span class="tabEnd">&nbsp;</span></span></caption>
<tr>
<th class="colFirst" scope="col">Modifier and Type</th>
<th class="colLast" scope="col">Method and Description</th>
</tr>
<tr id="i0" class="altColor">
<td class="colFirst"><code><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a></code></td>
<td class="colLast"><code><span class="memberNameLink"><a href="../../../../../org/apache/zookeeper/server/admin/Command.html#getDoc--">getDoc</a></span>()</code>
<div class="block">A string documentating this command (e.g., what it does, any arguments it
 takes).</div>
</td>
</tr>
<tr id="i1" class="rowColor">
<td class="colFirst"><code><a href="https://docs.oracle.com/javase/8/docs/api/java/util/Set.html?is-external=true" title="class or interface in java.util">Set</a>&lt;<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&gt;</code></td>
<td class="colLast"><code><span class="memberNameLink"><a href="../../../../../org/apache/zookeeper/server/admin/Command.html#getNames--">getNames</a></span>()</code>
<div class="block">The set of all names that can be used to refer to this command (e.g.,
 "configuration", "config", and "conf").</div>
</td>
</tr>
<tr id="i2" class="altColor">
<td class="colFirst"><code><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a></code></td>
<td class="colLast"><code><span class="memberNameLink"><a href="../../../../../org/apache/zookeeper/server/admin/Command.html#getPrimaryName--">getPrimaryName</a></span>()</code>
<div class="block">The name that is returned with the command response and that appears in
 the list of all commands.</div>
</td>
</tr>
<tr id="i3" class="rowColor">
<td class="colFirst"><code><a href="../../../../../org/apache/zookeeper/server/admin/CommandResponse.html" title="class in org.apache.zookeeper.server.admin">CommandResponse</a></code></td>
<td class="colLast"><code><span class="memberNameLink"><a href="../../../../../org/apache/zookeeper/server/admin/Command.html#run-org.apache.zookeeper.server.ZooKeeperServer-java.util.Map-">run</a></span>(<a href="../../../../../org/apache/zookeeper/server/ZooKeeperServer.html" title="class in org.apache.zookeeper.server">ZooKeeperServer</a>&nbsp;zkServer,
   <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html?is-external=true" title="class or interface in java.util">Map</a>&lt;<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>,<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&gt;&nbsp;kwargs)</code>
<div class="block">Run this command.</div>
</td>
</tr>
</table>
</li>
</ul>
</li>
</ul>
</div>
<div class="details">
<ul class="blockList">
<li class="blockList">
<!-- ============ METHOD DETAIL ========== -->
<ul class="blockList">
<li class="blockList"><a name="method.detail">
<!--   -->
</a>
<h3>Method Detail</h3>
<a name="getNames--">
<!--   -->
</a>
<ul class="blockList">
<li class="blockList">
<h4>getNames</h4>
<pre><a href="https://docs.oracle.com/javase/8/docs/api/java/util/Set.html?is-external=true" title="class or interface in java.util">Set</a>&lt;<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&gt;&nbsp;getNames()</pre>
<div class="block">The set of all names that can be used to refer to this command (e.g.,
 "configuration", "config", and "conf").</div>
</li>
</ul>
<a name="getPrimaryName--">
<!--   -->
</a>
<ul class="blockList">
<li class="blockList">
<h4>getPrimaryName</h4>
<pre><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&nbsp;getPrimaryName()</pre>
<div class="block">The name that is returned with the command response and that appears in
 the list of all commands. This should be a member of the set returned by
 getNames().</div>
</li>
</ul>
<a name="getDoc--">
<!--   -->
</a>
<ul class="blockList">
<li class="blockList">
<h4>getDoc</h4>
<pre><a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&nbsp;getDoc()</pre>
<div class="block">A string documentating this command (e.g., what it does, any arguments it
 takes).</div>
</li>
</ul>
<a name="run-org.apache.zookeeper.server.ZooKeeperServer-java.util.Map-">
<!--   -->
</a>
<ul class="blockListLast">
<li class="blockList">
<h4>run</h4>
<pre><a href="../../../../../org/apache/zookeeper/server/admin/CommandResponse.html" title="class in org.apache.zookeeper.server.admin">CommandResponse</a>&nbsp;run(<a href="../../../../../org/apache/zookeeper/server/ZooKeeperServer.html" title="class in org.apache.zookeeper.server">ZooKeeperServer</a>&nbsp;zkServer,
                    <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Map.html?is-external=true" title="class or interface in java.util">Map</a>&lt;<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>,<a href="https://docs.oracle.com/javase/8/docs/api/java/lang/String.html?is-external=true" title="class or interface in java.lang">String</a>&gt;&nbsp;kwargs)</pre>
<div class="block">Run this command. Commands take a ZooKeeperServer and String-valued
 keyword arguments and return a map containing any information
 constituting the response to the command. Commands are responsible for
 parsing keyword arguments and performing any error handling if necessary.
 Errors should be reported by setting the "error" entry of the returned
 map with an appropriate message rather than throwing an exception.</div>
<dl>
<dt><span class="paramLabel">Parameters:</span></dt>
<dd><code>zkServer</code> - </dd>
<dd><code>kwargs</code> - keyword -> argument value mapping</dd>
<dt><span class="returnLabel">Returns:</span></dt>
<dd>Map representing response to command containing at minimum:
    - "command" key containing the command's primary name
    - "error" key containing a String error message or null if no error</dd>
</dl>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>
<!-- ========= END OF CLASS DATA ========= -->
<!-- ======= START OF BOTTOM NAVBAR ====== -->
<div class="bottomNav"><a name="navbar.bottom">
<!--   -->
</a>
<div class="skipNav"><a href="#skip.navbar.bottom" title="Skip navigation links">Skip navigation links</a></div>
<a name="navbar.bottom.firstrow">
<!--   -->
</a>
<ul class="navList" title="Navigation">
<li><a href="../../../../../overview-summary.html">Overview</a></li>
<li><a href="package-summary.html">Package</a></li>
<li class="navBarCell1Rev">Class</li>
<li><a href="class-use/Command.html">Use</a></li>
<li><a href="package-tree.html">Tree</a></li>
<li><a href="../../../../../deprecated-list.html">Deprecated</a></li>
<li><a href="../../../../../index-all.html">Index</a></li>
<li><a href="../../../../../help-doc.html">Help</a></li>
</ul>
</div>
<div class="subNav">
<ul class="navList">
<li><a href="../../../../../org/apache/zookeeper/server/admin/AdminServerFactory.html" title="class in org.apache.zookeeper.server.admin"><span class="typeNameLink">Prev&nbsp;Class</span></a></li>
<li><a href="../../../../../org/apache/zookeeper/server/admin/CommandBase.html" title="class in org.apache.zookeeper.server.admin"><span class="typeNameLink">Next&nbsp;Class</span></a></li>
</ul>
<ul class="navList">
<li><a href="../../../../../index.html?org/apache/zookeeper/server/admin/Command.html" target="_top">Frames</a></li>
<li><a href="Command.html" target="_top">No&nbsp;Frames</a></li>
</ul>
<ul class="navList" id="allclasses_navbar_bottom">
<li><a href="../../../../../allclasses-noframe.html">All&nbsp;Classes</a></li>
</ul>
<div>
<script type="text/javascript"><!--
  allClassesLink = document.getElementById("allclasses_navbar_bottom");
  if(window==top) {
    allClassesLink.style.display = "block";
  }
  else {
    allClassesLink.style.display = "none";
  }
  //-->
</script>
</div>
<div>
<ul class="subNavList">
<li>Summary:&nbsp;</li>
<li>Nested&nbsp;|&nbsp;</li>
<li>Field&nbsp;|&nbsp;</li>
<li>Constr&nbsp;|&nbsp;</li>
<li><a href="#method.summary">Method</a></li>
</ul>
<ul class="subNavList">
<li>Detail:&nbsp;</li>
<li>Field&nbsp;|&nbsp;</li>
<li>Constr&nbsp;|&nbsp;</li>
<li><a href="#method.detail">Method</a></li>
</ul>
</div>
<a name="skip.navbar.bottom">
<!--   -->
</a></div>
<!-- ======== END OF BOTTOM NAVBAR ======= -->
<p class="legalCopy"><small>Copyright &#169; 2008&#x2013;2019 <a href="https://www.apache.org/">The Apache Software Foundation</a>. All rights reserved.</small></p>
</body>
</html>
