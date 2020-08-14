import React,{Component} from 'react';
import { StyleSheet, Text, View, Button, TouchableOpacity } from 'react-native';
import { Dimensions } from 'react-native';

export default class Timer extends Component {
    constructor(props) {
      super(props);
    }
      
    render() {
      const {timer, session, reset, pausePlay, started, workTimer, newTimerNumber, breakTimer} = this.props
      return (
        <View className="main-timer" style={style.mainTimer}>
          <Text id="timer-label" style={style.timerLabel}>{session ? "Session" : "Break"}</Text>
          <Text id="time-left" style={style.numbersText}>{session ? workTimer : breakTimer}</Text>
          {/* <p>{workTimer}</p>}*/}
          {/* <p>{breakTimer}</p>}*/}
          {/* <p>{newTimerNumber}</p>*/}
          <View className="btnContainer" style={style.btnContainer}>
            <TouchableOpacity onPress={pausePlay} style={style.appButtonContainer}>
              <Text style={style.timerBtn} id="start_stop">{started ? "| |" : "▶"}</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={reset} style={style.appButtonContainer}>
              <Text style={style.timerBtn} id="reset">↺</Text>
            </TouchableOpacity>
          </View>
        </View>
      )
    }
}

const style = StyleSheet.create({
  mainTimer: {
    width: 200,
    // borderWidth: 2,
    // borderColor: "white",
    // borderStyle: 'solid',
    shadowOffset: {width: 4, height: 4},
    shadowRadius: 4,
    // elevation: 1,
    borderRadius: 10,
    flexDirection: "column",
    alignItems: "center",
    alignSelf: "center",
    justifyContent: "space-between",
    marginBottom: 50,
    height:  Dimensions.get('window').height-300
  },

  timerLabel: {
    fontSize: 47,
    textShadowOffset: {width: 1, height: 1},
    textShadowColor: "white",
    textShadowRadius: 1,
    elevation: 4,
    textTransform: "uppercase",
    color: "#F95C6D",
    paddingBottom: 90,
  },

  timerBtn: {
    color: "#F95C6D",
    fontSize: 60,
    margin: "auto"
  },

  appButtonContainer: {
    backgroundColor: "#00000000",
    borderRadius: 10,
    paddingVertical: 10,
    paddingHorizontal: 12
  },

  btnContainer: {
    flexDirection: "row"
  },

  numbersText: {
    color: "white",
    fontSize: 60,
    fontFamily: 'Righteous',
    fontWeight: "900",
    textAlign: "center",
    margin: "auto"
  }
})