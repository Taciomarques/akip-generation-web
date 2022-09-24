/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ProcessUpdateComponent from '../../../../../../main/webapp/app/entities/akip-process/akip-process-update.vue';
import ProcessClass from '../../../../../../main/webapp/app/entities/akip-process/akip-process-update.component';
import AkipProcessService from '../../../../../../main/webapp/app/entities/akip-process/akip-process.service';

import AkipEntityService from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.service';

import AkipApplicationService from '../../../../../../main/webapp/app/entities/akip-application/akip-application.service';

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
  describe('Process Management Update Component', () => {
    let wrapper: Wrapper<ProcessClass>;
    let comp: ProcessClass;
    let processServiceStub: SinonStubbedInstance<AkipProcessService>;

    beforeEach(() => {
      processServiceStub = sinon.createStubInstance<AkipProcessService>(AkipProcessService);

      wrapper = shallowMount<ProcessClass>(ProcessUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          akipProcessService: () => processServiceStub,

          akipEntityService: () => new AkipEntityService(),

          akipApplicationService: () => new AkipApplicationService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.process = entity;
        processServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.process = entity;
        processServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(processServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProcess = { id: 123 };
        processServiceStub.find.resolves(foundProcess);
        processServiceStub.retrieve.resolves([foundProcess]);

        // WHEN
        comp.beforeRouteEnter({ params: { processId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.process).toBe(foundProcess);
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
