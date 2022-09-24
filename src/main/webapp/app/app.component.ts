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

  private collapsed: boolean = false;
  private isOnMobile: boolean = false;
  private hasAnyAuthorityValue: boolean = false;

  public get calculateMenuItems(): any {
    const menuItems: any = [
      {
        href: '/',
        title: 'Home',
        icon: 'fa fa-home',
      },
    ];

    if (this.authenticated) {
      menuItems.push(
        {
          href: '/my-candidate-tasks',
          title: 'My Tasks',
          icon: 'fa fa-tasks',
        },
        {
          title: 'Entities',
          icon: 'fa fa-th-list',
          child: [
            {
              href: '/akip-application',
              title: 'Appplication',
              icon: 'fa fa-folder',
            },
            {
              href: '/akip-process',
              title: 'Process',
              icon: 'fa fa-arrows-rotate',
            },
            {
              href: '/akip-entity',
              title: 'Entity',
              icon: 'fa fa-diagram-project',
            },
            // {
            //   href: '/process-definition/TravelPlanProcess/instances',
            //   title: 'Travel Plan Process',
            //   icon: '',
            // },
          ],
        }
      );
    }

    if (this.hasAnyAuthority(['ROLE_ADMIN'])) {
      menuItems.push({
        title: 'Admin',
        icon: 'fa fa-cogs',
        child: [
          {
            href: '/process-definitions',
            title: 'Process Definitions',
            icon: 'fa fa-asterisk',
          },
          {
            href: '/process-instances',
            title: 'Process Instances',
            icon: 'fa fa-asterisk',
          },
          {
            href: '/task-instances',
            title: 'Task Instances',
            icon: 'fa fa-asterisk',
          },
          {
            href: '/email-action-config',
            title: 'Email Config',
            icon: 'fa fa-asterisk',
          },
          {
            href: '/admin/user-management',
            title: 'User management',
            icon: 'fa fa-users',
          },
          {
            href: '/tenant',
            title: 'Tenant',
            icon: 'fa fa-asterisk',
          },
          {
            href: '/admin/metrics',
            title: 'Metrics',
            icon: 'fa fa-tachometer-alt',
          },
          {
            href: '/admin/health',
            title: 'Health',
            icon: 'fa fa-heart',
          },
          {
            href: '/admin/configuration',
            title: 'Configuration',
            icon: 'fa fa-cogs',
          },
          {
            href: '/admin/logs',
            title: 'Logs',
            icon: 'fa fa-tasks',
          },
          {
            href: '/admin/docs',
            title: 'API',
            icon: 'fa fa-book',
          },
          {
            href: './h2-console/',
            external: true,
            title: 'Database',
            icon: 'fa fa-database',
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
