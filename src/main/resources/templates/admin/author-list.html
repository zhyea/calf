<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<div th:replace="admin.header::header"></div>
<body>

<div class="wrapper">
    <div th:replace="admin.nav::nav"></div>

    <div class="container main">
        <div class="page-header">
            <h3><i class="glyphicon glyphicon-pencil"></i> 作者列表</h3>
        </div>

        <div th:replace="admin.alert::alert"></div>

        <div id="authorTableToolbar">
            <div class="btn-group">
                <a class="btn btn-default" href="/admin/author/settings">
                    <i class="glyphicon glyphicon-plus"></i>新增
                </a>
            </div>
        </div>
        <table id="authorTable"
               data-toggle="table"
               data-search="true"
               data-show-refresh=true
               data-classes="table table-hover table-borderless"
               data-toolbar="#authorTableToolbar"
               data-url="/api/author/data"
               data-id-field="id"
               data-sort-name="id"
               data-sort-order="desc"
               data-method="post"
               data-side-pagination='server'
               data-pagination=true
               data-page-size=30
               data-page-list=[15,30,50,All]>
            <thead>
            <tr>
                <th data-align="left" data-sortable="true" data-field="name" data-formatter="nameFormatter">姓名</th>
                <th data-align="left" data-sortable="true" data-field="country">国家</th>
                <th data-align="center" data-formatter="worksFormatter">作品</th>
                <th data-align="center" data-formatter="operateFormatter">操作</th>
            </tr>
            </thead>
        </table>
    </div>

    <div th:replace="admin.footer::footer"></div>
</div>

<script>
    function nameFormatter(value, row, index) {
        return '<a href="/admin/author/settings/' + row.id + '" target="_self">' + value + '</a>';
    }

    function worksFormatter(value, row, index) {
        return '<a href="/admin/author/works/' + row.id + '" target="_self"><span class="badge">' + row.workcount + '</span></a>';
    }

    function operateFormatter(value, row, index) {
        if (row.id > 1) {
            return '<a href="/admin/author/delete/' + row.id + '" target="_self">删除</a>';
        }
    }
</script>
</body>