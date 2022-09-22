/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import EntidadeUpdateComponent from '@/entities/entidade/entidade-update.vue';
import EntidadeClass from '@/entities/entidade/entidade-update.component';
import EntidadeService from '@/entities/entidade/entidade.service';

import ProcessService from '@/entities/process/process.service';

import ApplicationService from '@/entities/application/application.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Entidade Management Update Component', () => {
    let wrapper: Wrapper<EntidadeClass>;
    let comp: EntidadeClass;
    let entidadeServiceStub: SinonStubbedInstance<EntidadeService>;

    beforeEach(() => {
      entidadeServiceStub = sinon.createStubInstance<EntidadeService>(EntidadeService);

      wrapper = shallowMount<EntidadeClass>(EntidadeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          entidadeService: () => entidadeServiceStub,

          processService: () => new ProcessService(),

          applicationService: () => new ApplicationService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.entidade = entity;
        entidadeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entidadeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.entidade = entity;
        entidadeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entidadeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEntidade = { id: 123 };
        entidadeServiceStub.find.resolves(foundEntidade);
        entidadeServiceStub.retrieve.resolves([foundEntidade]);

        // WHEN
        comp.beforeRouteEnter({ params: { entidadeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.entidade).toBe(foundEntidade);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
