<#macro leftMenu indexUrl menuList currentUrl>

<script type="text/javascript">
    try{ace.settings.loadState('main-container')}catch(e){}
</script>
<div id="sidebar" class="sidebar responsive ace-save-state">
    <script type="text/javascript">
        try { ace.settings.loadState('sidebar') } catch (e) { }
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts" style="display: none;">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div>

    <ul class="nav nav-list">
        <li class="<#if indexUrl == currentUrl>active</#if>" style="display: none;">
            <a href="${indexUrl}">
                <i class="menu-icon fa fa-home"></i>
                <span class="menu-text"> 导航页 </span>
            </a>
            <b class="arrow"></b>
        </li>

        <#list menuList as item>
            <#assign isMath=false/>
            <#list item.subMenuList as subMenu>
                <#if subMenu.href == currentUrl><#assign isMath=true/><#break></#if>
            </#list>
            <li class="<#if isMath>active open</#if>">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa ${item.icon}"></i>
                    <span class="menu-text"> ${item.name} </span>
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <#list item.subMenuList as subMenu>
                        <li class="<#if subMenu.href== currentUrl>active</#if>">
                            <a href="${subMenu.href}" <#if subMenu.linkTarget==1>target="_blank"</#if>>
                                <i class="menu-icon fa ${subMenu.icon}"></i>
                                ${subMenu.name}
                            </a>
                            <b class="arrow"></b>
                        </li>
                    </#list>
                </ul>
            </li>
        </#list>
    </ul>

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
</div>

</#macro>