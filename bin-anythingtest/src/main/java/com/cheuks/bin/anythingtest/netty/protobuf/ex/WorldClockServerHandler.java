/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.cheuks.bin.anythingtest.netty.protobuf.ex;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import java.util.Calendar;
import java.util.TimeZone;

import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.Continent;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.DayOfWeek;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.LocalTime;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.LocalTimes;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.Location;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol.Locations;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WorldClockServerHandler extends SimpleChannelInboundHandler<Locations> {

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Locations locations) throws Exception {
		long currentTime = System.currentTimeMillis();

		LocalTimes.Builder builder = LocalTimes.newBuilder();
		for (Location l : locations.getLocationList()) {
			TimeZone tz = TimeZone.getTimeZone(toString(l.getContinent()) + '/' + l.getCity());
			Calendar calendar = getInstance(tz);
			calendar.setTimeInMillis(currentTime);

			builder.addLocalTime(LocalTime.newBuilder().setYear(calendar.get(YEAR)).setMonth(calendar.get(MONTH) + 1).setDayOfMonth(calendar.get(DAY_OF_MONTH)).setDayOfWeek(DayOfWeek.valueOf(calendar.get(DAY_OF_WEEK))).setHour(calendar.get(HOUR_OF_DAY)).setMinute(calendar.get(MINUTE)).setSecond(calendar.get(SECOND)).build());
		}

		ctx.write(builder.build());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	private static String toString(Continent c) {
		return c.name().charAt(0) + c.name().toLowerCase().substring(1);
	}
}
