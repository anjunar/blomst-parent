import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentOptionsComponent } from './options.component';

describe('OptionsComponent', () => {
  let component: CommentOptionsComponent;
  let fixture: ComponentFixture<CommentOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentOptionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
