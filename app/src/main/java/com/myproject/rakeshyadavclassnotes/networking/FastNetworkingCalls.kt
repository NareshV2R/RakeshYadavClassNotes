package com.myproject.rakeshyadavclassnotes.networking

import android.content.Context
import android.util.Log
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.io.File
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object FastNetworkingCalls {

    private const val TAG = "FastNetworking"

    fun makeRxCallObservable(jsonObservable: Observable<JSONObject>, compositeDisposable: CompositeDisposable,
                             apiTag: String, onApiResult: OnApiResult){

        jsonObservable.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { json -> json }
            .subscribe(object : Observer<JSONObject> {
                override fun onComplete() {
                    Log.v(TAG, "$apiTag makeRxCall RxNetworkCompleted")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(json: JSONObject) {
                    Log.v(TAG, "$apiTag makeRxCall onNext $json")
                    onApiResult.onApiSuccess(json)

                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "$apiTag makeRxCall onError $e")
                    if (e is ANError) {
                        Log.e(TAG, "$apiTag makeRxCall onError ${e.message}" + e.errorCode + "\n" + e.errorBody)
                        onApiResult.onApiError(e)

                    }
                }

            })
    }

    fun getRxCallGetObservable(url: String, tag: String, hashParams: HashMap<String, Any?> = HashMap()): Observable<JSONObject> {

        val reqBuilder = Rx2AndroidNetworking.get(url)

        reqBuilder.addQueryParameter(hashParams)

        try {
            Log.i("Rx_Method", "-------GET-------")
            Log.i("Rx_Tag", url)
            Log.i("Rx_Params", "" + JSONObject(hashParams))
        } catch (e: Exception) {

        }

        return reqBuilder.build().jsonObjectObservable
    }


    fun logError(tag: String, e: Throwable) {
        Log.i("RxError-$tag", "" + e)
        if (e is ANError) {
            Log.i("Rx-Error-$tag", "" + e.errorCode + "\n" + e.errorBody)

        }
    }

    interface OnApiResult {
        fun onApiSuccess(response: JSONObject?)
        fun onApiError(anError: ANError)
    }

}