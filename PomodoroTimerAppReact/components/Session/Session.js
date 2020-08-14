import React,{Component} from 'react';
import { StyleSheet, Text, View, Button, TouchableOpacity, Dimensions } from 'react-native';

export default class Session extends Component {
    constructor(props) {
      super(props);
    }
    
    render() {
      const {howLongWork, howLongWorkInc, howLongWorkDec} = this.props
      return (
        <View className="main-session" style={style.mainSession}>
          <Text id="session-label" style={style.h3}>Session Length</Text>
          <TouchableOpacity>
            <Text onPress={howLongWorkInc} style={style.timerBtn} id="session-increment" title="&#9650;">&#9650;</Text>
          </TouchableOpacity>
          <Text id="session-length" style={style.numbersText}>{howLongWork}</Text>
          <TouchableOpacity>
            <Text onPress={howLongWorkDec} style={style.timerBtn} id="session-decrement" title="&#8595;">&#9660;</Text>
          </TouchableOpacity>
        </View>
      )
    }
}

const style = StyleSheet.create({
  mainSession: {
    height:  Dimensions.get('window').height-600,
    alignSelf: "center",
    justifyContent: "space-evenly",
    alignItems: "center"
  },

  timerBtn: {
    color: "#F95C6D",
    fontSize: 60,
    margin: "auto"
  },

  numbersText: {
    color: "white",
    fontSize: 60,
    fontFamily: 'Righteous',
    fontWeight: "900",
    textAlign: "center",
    margin: "auto"
  },

  h3: {
    fontFamily: 'Roboto',
    fontSize: 20,
    color: "white",
    textAlign: "center",
    fontWeight: "bold"
  }
})