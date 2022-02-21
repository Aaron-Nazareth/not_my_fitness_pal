package com.group6.not_my_fitness_pal.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PersonServiceTest {
    private PersonDao personDao;
    private PersonService underTest;
    @BeforeEach
    void setUp(){
        personDao = Mockito.mock(PersonDao.class); //personDao property is set to mock of PersonDao class
        underTest = new PersonService(personDao); //underTest is set to a new instance of PersonService which uses the mocked interface
    }

    @Test
    void canGetAllPeople() {
    }

    @Test
    void canGetPersonById() {
        //Given
        Integer id = 1;
        given(personDao.getPersonById(id)).willReturn(new Person(1, "marcy", 23, 157.0, 47.0, 2000));
        //When
        Person actual = underTest.getPersonById(id);
        //Then
        //do we need argument capture here as well?
        Person expected = new Person(1, "marcy", 23, 157.0, 47.0, 2000);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldNotGetPersonByIdWhenIdIsNull() {
        //Given
        Integer id = null;
        given(personDao.getPersonById(id)).willReturn(null);
        //above line is in case getPersonById is called after the exception (but it shouldnt be)
        //When
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("id is invalid");
        //Then
        verify(personDao, never()).getPersonById(anyInt());
    }

    @Test
    void shouldNotGetPersonByIdWhenIdIsLessThanZero() {
        //Given
        Integer id = -1;
        given(personDao.getPersonById(id)).willReturn(null);
        //above line is in case getPersonById is called after the exception (but it shouldnt be)
        //When
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("id is invalid");
        //Then
        verify(personDao, never()).getPersonById(anyInt());
    }

    @Test
    void shouldNotGetPersonByIdWhenPersonIdDoesNotExist() {
        //Given
        Integer id = 100; //person with id 100 does not exist within the db
        given(personDao.getPersonById(id)).willReturn(null);
        //above line is in case getPersonById is called after the exception (but it shouldnt be)
        //When
        assertThatThrownBy(() -> underTest.getPersonById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Person with id "+ id +" doesn't exists");
        //Then
        //Question for colin/nelson, do we need argument capture to check if the same id is being used throughout
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(personDao).getPersonById(integerArgumentCaptor.capture());
        Integer actual = integerArgumentCaptor.getValue();
        assertThat(actual).isEqualTo(id);
    }




}