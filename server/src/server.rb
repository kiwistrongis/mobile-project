#!/usr/bin/ruby

require 'sinatra'
#require 'sinatra/json'
#require 'yajl'

set :bind, '0.0.0.0'
set :port, 8080

# door var
@@locked = true

helpers do
	def auth_check( user, token_given)
		token_file = "data/#{user}.tok"

		if File.exists?( token_file)
			token = File.read( token_file)
			return token_given == token
		else
			return false
		end
	end
end

post '/lock' do
	user = params['user']
	token = params['token']

	if auth_check( user, token)
		@@locked = true
		#json ok: { status: "locked" }
		"locked"
	else
		#json err: "token_refused"
		"err: token refused"
	end
end

post '/unlock' do
	user = params['user']
	token = params['token']

	if auth_check( user, token)
		@@locked = false
		#json ok: { status: "unlocked" }
		"unlocked"
	else
		#json err: "token_refused"
		"err: token refused"
	end
end

get '/status' do
	@@locked ? "locked" : "unlocked"
end
post '/status' do
	@@locked ? "locked" : "unlocked"
end

not_found do
	"404"
end
