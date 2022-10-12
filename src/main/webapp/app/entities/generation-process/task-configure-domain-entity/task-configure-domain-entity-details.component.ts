import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureDomainEntityService from './task-configure-domain-entity.service';
import { TaskConfigureDomainEntityContext } from './task-configure-domain-entity.model';

@Component
export default class TaskConfigureDomainEntityDetailsComponent extends mixins(JhiDataUtils) {
  private taskConfigureDomainEntityService: TaskConfigureDomainEntityService = new TaskConfigureDomainEntityService();
  private taskContext: TaskConfigureDomainEntityContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskConfigureDomainEntityService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  get akipEntityDomain() {
    return this.taskContext.generationProcess.akipProcess.entities.find(akipEntity => akipEntity.type == 'DOMAIN');
  }
}
