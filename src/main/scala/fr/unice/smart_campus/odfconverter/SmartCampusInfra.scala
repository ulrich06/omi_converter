/*
 * ************************************************************************
 *                  Université de Nice Sophia-Antipolis (UNS) -
 *                  Centre National de la Recherche Scientifique (CNRS)
 *                  Copyright © 2016 UNS, CNRS
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 3 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *
 *     Author: Cyril Cecchinel – Laboratoire I3S – cecchine@i3s.unice.fr
 * ***********************************************************************
 */

package fr.unice.smart_campus.odfconverter

import fr.i3s.modalis.cosmic.organizational._
import fr.i3s.modalis.cosmic.organizational.shared.{DoubleType, IntegerType, StringType}

/**
  * Created by Cyril Cecchinel - I3S Laboratory on 15/12/2015.
  */
object SmartCampusInfra {
  val timefield = AtomicField("time", Continuous(Some(IntegerType(0)), None))


  val SC_temperature_outdoor = Observation("TEMPERATURE_OUTDOOR", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(-20.0)), Some(DoubleType(50.0))))), Some(1.0))
  val SC_temperature_indoor = Observation("TEMPERATURE_INDOOR", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(0.0)), Some(DoubleType(50.0))))), Some(2.0))
  val SC_light = Observation("LIGHT", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(0.0)), Some(DoubleType(1023.0))))))
  val SC_noise = Observation("NOISE", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(0.0)), Some(DoubleType(1023.0))))))
  val SC_raw = Observation("RAW", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(0.0)), Some(DoubleType(1023.0))))))
  val SC_opening = Observation("OPENING", timefield, Set(AtomicField("v", Discrete(Set(StringType("OPEN"), StringType("CLOSED"))))))
  val SC_state = Observation("STATE", timefield, Set(AtomicField("v", Discrete(Set(StringType("ON"), StringType("OFF"))))))
  val SC_power = Observation("POWER", timefield, Set(AtomicField("v", Continuous(Some(DoubleType(0.0)), Some(DoubleType(2500.0))))), Some(50.0))

  val infrastructure = Container("Campus SophiaTech", EContainerType.Campus, Set(
    Periodic("TEMP_CAMPUS", 300, "http://smartcampus.unice.fr/sensors/TEMP_CAMPUS/data/", SC_temperature_outdoor),
    Container("Templiers Ouest", EContainerType.Building, Set(
      Container("4th floor", EContainerType.Floor, Set(
        EventBased("DOOR_SPARKS", "onChange", "http://smartcampus.unice.fr/sensors/DOOR_SPARKS/data/", SC_opening),
        Container("Coffee corner", EContainerType.OpenSpace, Set(
          Periodic("LIGHT_CAFE", 300, "http://smartcampus.unice.fr/sensors/LIGHT_CAFE/data/", SC_light),
          Periodic("TEMP_CAFEV", 300, "http://smartcampus.unice.fr/sensors/TEMP_CAFEV/data/", SC_temperature_indoor),
          EventBased("Window_Coffee", "onChange", "http://smartcampus.unice.fr/sensors/Window_Coffee/data/", SC_opening),
          EventBased("Coffee_machine_power", "onRaise", "http://smartcampus.unice.fr/sensors/Coffee_machine_power/data/", SC_power)
        )),
        Container("Sous repartiteur", EContainerType.Room, Set(
          EventBased("MW_power", "onRaise", "http://smartcampus.unice.fr/sensors/MW_power/data/", SC_power)
        )),
        Container("Modalis corridor", EContainerType.Corridor, Set(
          EventBased("Window_Modalis", "onChange", "http://smartcampus.unice.fr/sensors/Window_Coffee/data/", SC_opening),
          Periodic("TEMP_CORRIDOR", 300, "http://smartcampus.unice.fr/sensors/TEMP_CORRIDOR/data/", SC_temperature_indoor),
          Periodic("NOISE_SPARKS_CORRIDOR", 2, "http://smartcampus.unice.fr/sensors/NOISE_SPARKS_CORRIDOR/data/", SC_noise),
          Container("Office 442", EContainerType.Room, Set(
            Periodic("TEMP_442V", 60, "http://smartcampus.unice.fr/sensors/TEMP_442V/data/", SC_temperature_indoor)
          )),
          Container("Office 443", EContainerType.Room, Set(
            EventBased("DOOR443STATE", "onChange", "http://smartcampus.unice.fr/sensors/DOOR443STATE/data/", SC_opening),
            Periodic("LIGHT_443", 300, "http://smartcampus.unice.fr/sensors/LIGHT_443/data", SC_light),
            Periodic("TEMP_TEMOIN", 60, "http://smartcampus.unice.fr/sensors/LIGHT_443/data", SC_temperature_indoor),
            Periodic("TEMP_XP", 60, "http://smartcampus.unice.fr/sensors/LIGHT_443/data", SC_temperature_indoor),
            Periodic("TEMP_443V", 300, "http://smartcampus.unice.fr/sensors/TEMP_442V/data/", SC_temperature_indoor),
            Periodic("AC_443", 300, "http://smartcampus.unice.fr/sensors/AC_443/data/", SC_temperature_indoor),
            EventBased("WINDOW443STATE", "onChange", "http://smartcampus.unice.fr/sensors/WINDOW443STATE/data/", SC_opening),
            EventBased("PRESENCE_443", "onChange", "http://smartcampus.unice.fr/sensors/PRESENCE_443/data/", SC_raw),
            EventBased("Coffee_power", "onRaise", "http://smartcampus.unice.fr/sensors/Coffee_power/data/", SC_power),
            EventBased("Office_443_Power", "onRaise", "http://smartcampus.unice.fr/sensors/Office_443_Power/data/", SC_power),
            Periodic("HEATING_443", 300, "http://smartcampus.unice.fr/sensors/HEATING_443/data/", SC_state),
            Periodic("AC_443STATE", 300, "http://smartcampus.unice.fr/sensors/AC_443STATE/data/", SC_state)
          )),
          Container("Office 444", EContainerType.Room, Set(
            Periodic("TEMP_444V", 300, "http://smartcampus.unice.fr/sensors/TEMP_444V/data/", SC_temperature_indoor),
            Periodic("LIGHT_444", 300, "http://smartcampus.unice.fr/sensors/LIGHT_444/data/", SC_light)
          )),
          Container("Office 445", EContainerType.Room, Set(
            Periodic("TEMP_445", 300, "http://smartcampus.unice.fr/sensors/TEMP_445V/data/", SC_temperature_indoor),
            Periodic("LIGHT_445", 300, "http://smartcampus.unice.fr/sensors/LIGHT_445/data", SC_light)
          )),
          Container("Office 449", EContainerType.Room, Set(
            Periodic("TEMP_449V", 300, "http://smartcampus.unice.fr/sensors/TEMP_449V/data/", SC_temperature_indoor)
          ))
        ))
      ))
    ))
  ))

  val catalog = Catalog("SmartCampus", infrastructure, Set(SC_temperature_indoor, SC_temperature_outdoor, SC_light, SC_noise, SC_power, SC_raw, SC_opening, SC_state))
}
