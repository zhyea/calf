<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>


<div th:fragment="nav">
    <div class="container header">
        <a th:if="${null==logo}">&nbsp;</a>
        <a th:if="${null!=logo}" href="/"><img th:src="${logo}" width="100%" height="100%"/></a>
    </div>
    <div class="container navigator">
        <nav class="navbar navbar-default">
            <div class="navbar-header">
                <button type="button"
                        class="navbar-toggle collapsed"
                        data-toggle="collapse"
                        data-target="#navbar"
                        aria-controls="navbar"
                        aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">
                    <i class="glyphicon glyphicon-home"></i> [[${siteName}]]
                    <span class="sr-only">(current)</span>
                </a>
            </div>

            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <div th:if="${!#lists.isEmpty(nav.children)}"
                         th:remove="tag"
                         th:each="n:${nav.children}"
                         th:object="${n}">
                        <li class="dropdown" th:if="${!#lists.isEmpty(n.children)}">
                            <a th:href="${url}"
                               class="dropdown-toggle"
                               data-toggle="dropdown"
                               role="button"
                               aria-haspopup="true"
                               aria-expanded="false">[[${n.name}]]<span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <div th:each="sub:*{children}" th:remove="tag" th:object="${sub}">
                                    <li>
                                        <a th:href="*{url}" th:text="*{name}"></a>
                                    </li>
                                </div>
                            </ul>
                        </li>
                        <li th:if="${#lists.isEmpty(n.children)}">
                            <a th:href="*{url}" th:text="*{name}"></a></li>
                    </div>
                </ul>
            </div>
        </nav>
    </div>
</div>

</body>
</html>
