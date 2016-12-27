package com.example.vishwasmittal.droidwars;



public class TargetLayoutClass {

    private int _id;
    private String _name, _house, _reason, _status, _killPlace, _killWay, _imgAddr;

    public TargetLayoutClass() {
        _name = "";
        _house = "";
        _reason = "";
        _status = "Not Killed";
        _killPlace = "";
        _killWay = "";
        _imgAddr = "";
    }

    //Setters
    public void set_house(String _house) {
        this._house = _house;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_imgAddr(String _imgAddr) {
        this._imgAddr = _imgAddr;
    }

    public void set_killPlace(String _killPlace) {
        this._killPlace = _killPlace;
    }

    public void set_killWay(String _killWay) {
        this._killWay = _killWay;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_reason(String _reason) {
        this._reason = _reason;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    //Getters
    public String get_house() {
        return _house;
    }

    public int get_id() {
        return _id;
    }

    public String get_imgAddr() {
        return _imgAddr;
    }

    public String get_killPlace() {
        return _killPlace;
    }

    public String get_killWay() {
        return _killWay;
    }

    public String get_name() {
        return _name;
    }

    public String get_reason() {
        return _reason;
    }

    public String get_status() {
        return _status;
    }
}
