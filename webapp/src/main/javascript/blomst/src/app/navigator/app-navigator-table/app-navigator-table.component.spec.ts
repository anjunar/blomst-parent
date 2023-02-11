import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppNavigatorTableComponent } from './app-navigator-table.component';

describe('AppNavigatorTableComponent', () => {
  let component: AppNavigatorTableComponent;
  let fixture: ComponentFixture<AppNavigatorTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppNavigatorTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppNavigatorTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
