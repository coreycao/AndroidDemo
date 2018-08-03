package com.corey.basic.repository.mock;

import com.corey.basic.repository.entity.Ticket;
import com.corey.basic.repository.entity.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caosanyang on 2018/7/20.
 */
public class MockServer {

  public static List<Trailer> getTrailerData() {
    List<Trailer> trailers = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      trailers.add(Trailer.newInstance(i));
    }
    return trailers;
  }

  public static List<Ticket> getTicketData() {
    List<Ticket> tickets = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      tickets.add(Ticket.newInstance(i));
    }
    return tickets;
  }
}