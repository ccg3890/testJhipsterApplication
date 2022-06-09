/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RoutineMainUpdateComponent from '@/entities/routine-main/routine-main-update.vue';
import RoutineMainClass from '@/entities/routine-main/routine-main-update.component';
import RoutineMainService from '@/entities/routine-main/routine-main.service';

import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('RoutineMain Management Update Component', () => {
    let wrapper: Wrapper<RoutineMainClass>;
    let comp: RoutineMainClass;
    let routineMainServiceStub: SinonStubbedInstance<RoutineMainService>;

    beforeEach(() => {
      routineMainServiceStub = sinon.createStubInstance<RoutineMainService>(RoutineMainService);

      wrapper = shallowMount<RoutineMainClass>(RoutineMainUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          routineMainService: () => routineMainServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.routineMain = entity;
        routineMainServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routineMainServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.routineMain = entity;
        routineMainServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(routineMainServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRoutineMain = { id: 'ABC' };
        routineMainServiceStub.find.resolves(foundRoutineMain);
        routineMainServiceStub.retrieve.resolves([foundRoutineMain]);

        // WHEN
        comp.beforeRouteEnter({ params: { routineMainId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.routineMain).toBe(foundRoutineMain);
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
