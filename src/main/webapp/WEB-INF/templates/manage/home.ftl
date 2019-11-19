<#include "/controls/apptop.ftl"/>
<#include "/controls/topbar.ftl"/>
<!-- 加载顶部bar -->
<@topBar title="${title}" titleUrl="/manage/home" currentUser=user icon="fa-home" />

<div class="main-container ace-save-state" id="main-container">

    <div class="main-content">
        <div class="main-content-inner">

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <ul style="text-align: center;margin-left: 50px">
                            <#list appList as item>
                                <li class="fa" style="float:left;margin: 20px;">
                                    <a href="${item.link}" target="<#if item.linktarget==0>_self<#else>_blank</#if>">
                                    <div class="fa ${item.smallicon} pink" style="margin-bottom: 10px;font-size: 400%"></div>
                                    <div class="bigger-120 pink">${item.name}</div>
                                    </a>
                                </li>
                            </#list>
                            <#list thirdAppList as item>
                                <li class="fa" style="float:left; margin: 20px;">
                                    <a href="/manage/ssoLogin?appKey=${item.appkey}&auth=${item.auth}" target="<#if item.linktarget==0>_self<#else>_blank</#if>">
                                    <div class="fa ${item.icon} pink" style="margin-bottom: 10px;font-size:400%"></div>
                                    <div class="bigger-120 pink">${item.name}</div>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->

    <!-- 加载底部版权 -->
    <#include "/controls/footer.ftl"/>

</div>

<#include "/controls/appbottom.ftl"/>

		<script type="text/javascript">
            jQuery(function($) {

            });
        </script>
