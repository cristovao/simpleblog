<%-- <%@ include file="/common.jsp" %> --%>

<simpleblog:list-post var="post" date="${date}">
	<h1><a href="${post.link}">${post.title}</a></h1>
	<div>${post.body}</div>
</simpleblog:list-post>

<simpleblog:next-page var="next" >
	<a href="${next}">Próximo</a>
</simpleblog:next-page>

<simpleblog:before-page var="before" >
	<a href="${before}">Anterior</a>
</simpleblog:before-page>

