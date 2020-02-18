<?php
defined('BASEPATH') OR exit('No direct script access allowed');
isset($ctx_theme) OR exit('No base url exists')
?>


<script charset="utf-8" src="<?= $ctx_theme ?>/static/js/reader.js" type="text/javascript"></script>

<div class="container notice">
	<ol class="breadcrumb">
		<li><a href="<?= $ctx_site ?>"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
		<li><a href="<?= $ctx_site . '/fe/cat/' . $cat_id ?>"><?= $cat_name ?></a></li>
		<li><a href="<?= $ctx_site . '/fe/work/' . $work_id ?>"><?= $work_name ?></a></li>
		<li class="active"><?= $name ?></li>
	</ol>
</div>

<div class="main">
	<div class="row readerTools">
		<script type="text/javascript">
            if (system.win || system.mac || system.xll) {
                readerSet();
            }
		</script>
	</div>

	<div class="row" id="contentContainer">

		<div class="row chapter-name">
            <?= $name ?>
		</div>

		<div class="row chapter-nav">
            <?php if ($last > 0): ?><a href="<?= $ctx_site . '/fe/chapter/' . $work_id . '/' . $last ?>">
					上一章</a> <?php else: ?> <a>无</a> <?php endif; ?> ← <a
					href="<?= $ctx_site . '/fe/work/' . $work_id ?>">返回目录</a> → <?php if ($next > 0): ?><a
				href="<?= $ctx_site . '/fe/chapter/' . $work_id . '/' . $next ?>">
					下一章</a> <?php else: ?> <a>没有了</a> <?php endif; ?>
		</div>

		<div class="row chapter-content" id="contentText" style=""><?= $content ?></div>

		<div class="row chapter-nav">
            <?php if ($last > 0): ?><a href="<?= $ctx_site . '/fe/chapter/' . $work_id . '/' . $last ?>">
					上一章</a> <?php else: ?> <a>无</a> <?php endif; ?> ← <a
					href="<?= $ctx_site . '/fe/work/' . $work_id ?>">返回目录</a> → <?php if ($next > 0): ?><a
				href="<?= $ctx_site . '/fe/chapter/' . $work_id . '/' . $next ?>">
					下一章</a> <?php else: ?> <a>没有了</a> <?php endif; ?>
		</div>
	</div>

</div>


<script type="text/javascript">
    window.addEventListener('load', LoadReadSet, false);
</script>
