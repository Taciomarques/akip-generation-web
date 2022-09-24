import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAkipApplication } from '@/shared/model/akip-application.model';
import AkipApplicationService from './akip-application.service';

@Component
export default class AKipApplicationDetails extends Vue {
  @Inject('akipApplicationService') private akipApplicationService: () => AkipApplicationService;
  public akipApplication: IAkipApplication = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipApplicationId) {
        vm.retrieveApplication(to.params.akipApplicationId);
      }
    });
  }

  public retrieveApplication(applicationId) {
    this.akipApplicationService()
      .find(applicationId)
      .then(res => {
        this.akipApplication = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
