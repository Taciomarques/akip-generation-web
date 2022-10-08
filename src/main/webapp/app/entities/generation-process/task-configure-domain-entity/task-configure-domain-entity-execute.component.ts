import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskConfigureDomainEntityService from './task-configure-domain-entity.service';
import { TaskConfigureDomainEntityContext } from './task-configure-domain-entity.model';

@Component
export default class TaskConfigureDomainEntityExecuteComponent extends mixins(JhiDataUtils) {
  private taskConfigureDomainEntityService: TaskConfigureDomainEntityService = new TaskConfigureDomainEntityService();
  private taskContext: TaskConfigureDomainEntityContext = {};
  public isSaving = false;
  public isAkipEntityInvalid = true;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.isSaving = true;
    this.taskConfigureDomainEntityService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete(requiredConfigureOtherAkipEntityDomain: string) {
    this.isSaving = true;
    this.taskContext.generationProcess.data.requiredConfigureOtherAkipEntityDomain = requiredConfigureOtherAkipEntityDomain;

    this.taskConfigureDomainEntityService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
