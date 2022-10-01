import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAkipEntity, AkipEntity } from '@/shared/model/akip-entity.model';
import AkipEntityService from './akip-entity.service';

@Component
export default class AkipEntityUpdate extends Vue {
  @Inject('akipEntityService') private akipEntityService: () => AkipEntityService;
  public akipEntity: IAkipEntity = new AkipEntity();
  public isSaving = false;
  public currentLanguage = '';
  public isAkipEntityInvalid = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.akipEntityId) {
        vm.retrieveEntity(to.params.akipEntityId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.akipEntity.id) {
      this.akipEntityService()
        .update(this.akipEntity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipEntity.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.akipEntityService()
        .create(this.akipEntity)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('akipGenerationWebApp.akipEntity.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveEntity(entityId): void {
    this.akipEntityService()
      .find(entityId)
      .then(res => {
        this.akipEntity = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
