
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    
    <link href="${RESOURCE}/image/common/favicon.ico" type="image/vnd.microsoft.icon" rel="shortcut icon" />
    <link href="${RESOURCE}/image/common/favicon.ico" type="image/vnd.microsoft.icon" rel="icon" />
    
    <script type="text/javascript">
        (function() {
            contextPath = '${pageContext.request.contextPath}';
            hostName = window.location.href;
            if (contextPath == '') {
                hostName = hostName.substring('http://'.length);
                if (hostName.indexOf('/') != -1) {
                    hostName = hostName.substring(0, hostName .indexOf('/'));
                }
            } else {
                hostName = hostName.substring('http://'.length, hostName.indexOf(contextPath));
            }
        })();
    </script>
