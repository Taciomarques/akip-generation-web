import Vue from 'vue';
import Component from 'vue-class-component';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import LoginForm from '@/account/login-form/login-form.vue';

import '@/shared/config/dayjs';
import AccountService from '@/account/account.service';
import { Inject } from 'vue-property-decorator';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'login-form': LoginForm,

    'jhi-footer': JhiFooter,
  },
})
export default class App extends Vue {
  @Inject('accountService') private accountService: () => AccountService;

  private collapsed: boolean = true;
  private isOnMobile: boolean = false;
  private hasAnyAuthorityValue: boolean = false;

  public get calculateMenuItems(): any {
    const menuItems: any = [
      {
        href: '/',
        title: `${this.$t('global.menu.home')}`,
        icon: 'fa fa-home',
        class: 'rounded',
      },
    ];

    if (this.authenticated) {
      menuItems.push(
        {
          href: '/my-candidate-tasks',
          title: `${this.$t('global.menu.entities.myCandidateTasks')}`,
          icon: 'fa fa-tasks',
          class: 'rounded',
        },
        {
          title: `${this.$t('global.menu.entities.main')}`,
          icon: 'fa fa-th-list',
          class: 'rounded',
          child: [
            {
              href: '/akip-application',
              title: `${this.$t('global.menu.entities.application')}`,
              icon: 'fa fa-folder',
              class: 'rounded',
            },
            {
              href: '/akip-process',
              title: `${this.$t('global.menu.entities.process')}`,
              icon: 'fa fa-arrows-rotate',
              class: 'rounded',
            },
            {
              href: '/akip-entity',
              title: `${this.$t('global.menu.entities.entity')}`,
              icon: 'fa fa-diagram-project',
              class: 'rounded',
            },
            {
              href: '/process-definition/GenerationProcess/instances',
              title: `${this.$t('global.menu.entities.generationProcess')}`,
              icon: 'fa fa-gear',
              class: 'rounded',
            },
          ],
        }
      );
    }

    if (this.hasAnyAuthority(['ROLE_ADMIN'])) {
      menuItems.push({
        title: `${this.$t('global.menu.admin.main')}`,
        icon: 'fa fa-cogs',
        class: 'rounded',
        child: [
          {
            href: '/process-definitions',
            title: `${this.$t('global.menu.entities.processDefinition')}`,
            icon: 'fa fa-asterisk',
            class: 'rounded',
          },
          {
            href: '/process-instances',
            title: `${this.$t('global.menu.entities.processInstance')}`,
            icon: 'fa fa-asterisk',
            class: 'rounded',
          },
          {
            href: '/task-instances',
            title: `${this.$t('global.menu.entities.taskInstance')}`,
            icon: 'fa fa-asterisk',
            class: 'rounded',
          },
          {
            href: '/admin/user-management',
            title: `${this.$t('global.menu.admin.userManagement')}`,
            icon: 'fa fa-users',
            class: 'rounded',
          },
          {
            href: '/admin/metrics',
            title: `${this.$t('global.menu.admin.metrics')}`,
            icon: 'fa fa-tachometer-alt',
            class: 'rounded',
          },
          {
            href: '/admin/health',
            title: `${this.$t('global.menu.admin.health')}`,
            icon: 'fa fa-heart',
            class: 'rounded',
          },
          {
            href: '/admin/configuration',
            title: `${this.$t('global.menu.admin.configuration')}`,
            icon: 'fa fa-cogs',
            class: 'rounded',
          },
          {
            href: '/admin/logs',
            title: `${this.$t('global.menu.admin.logs')}`,
            icon: 'fa fa-tasks',
            class: 'rounded',
          },
          {
            href: '/admin/docs',
            title: `${this.$t('global.menu.admin.apidocs')}`,
            icon: 'fa fa-book',
            class: 'rounded',
          },
          {
            href: './h2-console/',
            external: true,
            title: `${this.$t('global.menu.admin.database')}`,
            icon: 'fa fa-database',
            class: 'rounded',
          },
        ],
      });
    }

    return menuItems;
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValue = value;
      });
    return this.hasAnyAuthorityValue;
  }

  public onToggleCollapse(collapsed) {
    this.collapsed = collapsed;
  }
}
