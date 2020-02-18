<?php
defined('BASEPATH') OR exit('No direct script access allowed');
isset($ctx_theme) OR exit('No base url exists')
?>


<div class="container notice">
	<ol class="breadcrumb">
		<li><a href="<?= $ctx_site ?>"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
		<li><?= $name ?></li>
	</ol>
</div>

<div class="container main">


	<div style="margin:0 -15px;">
		<div class="work-header">
			<span class="title" style="font-size: 18px;"><?= $name ?></span>
		</div>

		<div class="work-neck row" style="padding: 12px 16px">
            <?= $bio ?>
		</div>
	</div>

	<div class="page-header">
		<h3><span class="glyphicon glyphicon-book"></span>&nbsp;作品列表</h3>
	</div>
	<div class="row category">
        <?php foreach ($works as $w): ?>
			<div class="col-md-6 col-xs-12 work">
				<div class="cover">
					<a href="<?= $ctx_site . '/fe/work/' . $w['id'] ?>">
						<img src="<?= $ctx_upload . '/' . $w['cover'] ?>" width="120px" height="156px"/>
					</a>
				</div>
				<div class="brief">
					<div class="title"><a href="<?= $ctx_site . '/fe/work/' . $w['id'] ?>"><?= $w['name'] ?></a></div>
					<div class="author">
						<a href="<?= $ctx_site . '/fe/author/' . $w['author_id'] ?>"><?= $w['author'] ?></a>
					</div>
					<div class="intro"><?= substr($w['brief'], 0, 64) . '...' ?></div>
				</div>
			</div>
        <?php endforeach; ?>
	</div>


	<div class="pagination">
        <?php
        for ($x = 1; $x <= $total + 1; $x++) {
            $active = $x == $curr ? 'class="active"' : '';
            echo '<a href="' . $ctx_site . '/fe/author/' . $author_id . '/' . $x . '" ' . $active . '>' . $x . '</a>';
        }
        ?>
	</div>
</div>
