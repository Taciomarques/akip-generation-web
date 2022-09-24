/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApplicationUpdateComponent from '../../../../../../main/webapp/app/entities/akip-application/akip-application-update.vue';
import ApplicationClass from '../../../../../../main/webapp/app/entities/akip-application/akip-application-update.component';
import AkipApplicationService from '../../../../../../main/webapp/app/entities/akip-application/akip-application.service';

import AkipEntityService from '../../../../../../main/webapp/app/entities/akip-entity/akip-entity.service';

import AkipProcessService from '../../../../../../main/webapp/app/entities/akip-process/akip-process.service';

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
  describe('Application Management Update Component', () => {
    let wrapper: Wrapper<ApplicationClass>;
    let comp: ApplicationClass;
    let applicationServiceStub: SinonStubbedInstance<AkipApplicationService>;

    beforeEach(() => {
      applicationServiceStub = sinon.createStubInstance<AkipApplicationService>(AkipApplicationService);

      wrapper = shallowMount<ApplicationClass>(ApplicationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          akipApplicationService: () => applicationServiceStub,

          akipEntityService: () => new AkipEntityService(),

          akipProcessService: () => new AkipProcessService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.application = entity;
        applicationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.application = entity;
        applicationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(applicationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApplication = { id: 123 };
        applicationServiceStub.find.resolves(foundApplication);
        applicationServiceStub.retrieve.resolves([foundApplication]);

        // WHEN
        comp.beforeRouteEnter({ params: { applicationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.application).toBe(foundApplication);
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
