import { Component } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateDomainEntityService from './task-generate-domain-entity.service';
import { TaskGenerateDomainEntityContext } from './task-generate-domain-entity.model';

@Component
export default class TaskGenerateDomainEntityExecuteComponent extends mixins(JhiDataUtils) {
  private taskGenerateDomainEntityService: TaskGenerateDomainEntityService = new TaskGenerateDomainEntityService();
  private taskContext: TaskGenerateDomainEntityContext = {};
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
    this.taskGenerateDomainEntityService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete(requiredGenerateOtherDomainEntity: string) {
    this.isSaving = true;
    this.taskContext.generationProcess.data.requiredGenerateOtherDomainEntity = requiredGenerateOtherDomainEntity;

    this.taskGenerateDomainEntityService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
