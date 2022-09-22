import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApplication } from '@/shared/model/application.model';
import ApplicationService from './application.service';

@Component
export default class ApplicationDetails extends Vue {
  @Inject('applicationService') private applicationService: () => ApplicationService;
  public application: IApplication = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.applicationId) {
        vm.retrieveApplication(to.params.applicationId);
      }
    });
  }

  public retrieveApplication(applicationId) {
    this.applicationService()
      .find(applicationId)
      .then(res => {
        this.application = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
