import { Component, Watch } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import TaskGenerateProcessBindingService from './task-generate-process-binding.service';
import { TaskGenerateProcessBindingContext } from './task-generate-process-binding.model';
import { AkipEntity } from '@/shared/model/akip-entity.model';
import { required } from 'vuelidate/lib/validators';

const validations = {
  akipEntityDomain: {
    required,
  },
};

@Component({ validations })
export default class TaskGenerateProcessBindingExecuteComponent extends mixins(JhiDataUtils) {
  private taskGenerateProcessBindingService: TaskGenerateProcessBindingService = new TaskGenerateProcessBindingService();
  private taskContext: TaskGenerateProcessBindingContext = {};
  public isSaving = false;
  public isAkipEntityInvalid = true;
  public akipEntityDomain: AkipEntity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.isSaving = true;
    this.taskGenerateProcessBindingService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
      this.isSaving = false;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.isSaving = true;
    this.taskContext.generationProcess.akipProcess.entities.push(this.akipEntityDomain);

    this.taskGenerateProcessBindingService.complete(this.taskContext).then(res => {
      this.isSaving = false;
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}

  public setProcessBindingFieldsAndRelationshipsOfAkipEntityDomain() {
    this.taskContext.akipEntityProcessBinding.fields = this.akipEntityDomain.fields;
    this.taskContext.akipEntityProcessBinding.relationships = this.akipEntityDomain.relationships;
  }
}
