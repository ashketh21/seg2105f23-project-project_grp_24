package com.example.grimpeurscyclingclubgcc;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

    public class EnrolledEventListTests {

        @Mock
        private Activity mockActivity;

        private EnrolledEventList enrolledEventList;

        //Initial set up before the tests
        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            List<String> events = new ArrayList<>();
            enrolledEventList = new EnrolledEventList(mockActivity, events, "testUsername");
        }

        //Simple test that checks if an invalid rating would be invalid
        @Test
        public void InvalidRatingTest(){
            String fakeRating = "6";

            assertFalse(enrolledEventList.validateRating(fakeRating));

        }

        //Simple test that checks if an valid rating would be valid
        @Test
        public void ValidRatingTest(){
            String fakeRating = "3";

            assertTrue(enrolledEventList.validateRating(fakeRating));

        }
        @Test
        //Simulates an instance of 'EnrolledEventList" with a valid event
        public void testGetView_WithValidEvent() {
            List<String> events = new ArrayList<>();
            events.add("Event example");
            enrolledEventList = new EnrolledEventList(mockActivity, events, "testUsername");

            ViewGroup mockParent = mock(ViewGroup.class);

            //Initalize the view
            View view = enrolledEventList.getView(0, null, mockParent);

            assertNotNull(view);

            TextView textView = view.findViewById(R.id.eventText);
            assertNotNull(textView);

            Button reviewButton = view.findViewById(R.id.reviewButton);
            assertNotNull(reviewButton);

            // Simulate a click on the review button
            reviewButton.performClick();

            // Check if the AlertDialog is created
            assertNotNull(enrolledEventList.getAlertDialog());
        }

        @Test
        //Simulates an instance of 'EnrolledEventList" with an empty event
        public void testGetView_WithEmptyEventList() {
            ViewGroup mockParent = mock(ViewGroup.class);
            //Initalize the view
            View view = enrolledEventList.getView(0, null, mockParent);

            assertNotNull(view);

            TextView textView = view.findViewById(R.id.eventText);
            assertNotNull(textView);

            Button reviewButton = view.findViewById(R.id.reviewButton);
            assertNotNull(reviewButton);

            // Simulate a click on the review button
            reviewButton.performClick();

            // Check if the Alert dialog is created
            assertNotNull(enrolledEventList.getAlertDialog());
        }

        //Simple test that checks if an invalid rating would be invalid
        @Test
        public void EmptyRatingTest(){
            String fakeRating = "";

            assertFalse(enrolledEventList.validateRating(fakeRating));

        }


    }


