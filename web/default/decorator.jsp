<%@ include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Simple Blog: <simpleblog:title/></title>
		<script type="text/javascript" src="/default/js/jquery-2.0.1.js" ></script>
		<simpleblog:script var="script" type="script">
			<script type="text/javascript" src="${script}" ></script>
		</simpleblog:script>
		<simpleblog:script var="link" type="css">
			<link href="${link}" media="screen, projection" rel="stylesheet" type="text/css"></link>
		</simpleblog:script>
		<script type='text/javascript'>
			jQuery(document).ready({
				<simpleblog:script var="script" type="content">
					${script}
				</simpleblog:script>
			});
		</script>
	</head>
	<body>
		<simpleblog:body/>
	</body>
</html>